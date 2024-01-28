package com.xxx.jingdong.controller;


import com.xxx.jingdong.config.FileUploadConfig;
import com.xxx.jingdong.enums.ResultEnum;
import com.xxx.jingdong.pojo.Product;
import com.xxx.jingdong.pojo.ProductCategory;
import com.xxx.jingdong.pojo.Shop;
import com.xxx.jingdong.service.ProductCategoryService;
import com.xxx.jingdong.service.ProductService;
import com.xxx.jingdong.service.ShopService;
import com.xxx.jingdong.utils.UploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;


/**
 * Author；Aaron
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService categoryService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private FileUploadConfig fileUploadConfig;

    /**
     * 渲染超市分类
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    //请求参数注解可以设置默认值，由名称以及默认value对应
    public ModelAndView getProducts(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                    @RequestParam(value = "size",defaultValue = "10") Integer size,
                                    HttpSession session){
        String sellerId=(String) session.getAttribute("sellerId");
        PageRequest request=PageRequest.of(page-1,size);
        Page<Product> productList=productService.findAllByPage(request,sellerId);
        ModelAndView mv=new ModelAndView();
        mv.addObject("productList",productList);
        mv.addObject("page",page);
        mv.setViewName("/product/list");
        return mv;
    }

    @GetMapping("/toUp")
    //请求参数注解可以设置默认值，由名称以及默认value对应
    public ModelAndView toUp(@RequestParam(value = "productId") Integer productId){
        ModelAndView mv=new ModelAndView();
        mv.addObject("url","/jingdong/seller/product/list");
        try {
            productService.productStatusUp(productId);
        }catch (Exception e){
            log.error("【商品上架】失败{}",e);
            mv.addObject("msg",e.getMessage());
            mv.setViewName("common/error");
        };
        mv.addObject("msg", ResultEnum.PRODUCT_UP_SUCCESS.getMessage());
        mv.setViewName("common/success");
        return mv;
    }

    @GetMapping("/toDown")
    //请求参数注解可以设置默认值，由名称以及默认value对应
    public ModelAndView toDown(@RequestParam(value = "productId") Integer productId){
        ModelAndView mv=new ModelAndView();
        mv.addObject("url","/jingdong/seller/product/list");
        try {
            productService.productStatusDrop(productId);
        }catch (Exception e){
            log.error("【商品下架】失败{}",e);
            mv.addObject("msg",e.getMessage());
            mv.setViewName("common/error");
        };
        mv.addObject("msg", ResultEnum.PRODUCT_DOWN_SUCCESS.getMessage());
        mv.setViewName("common/success");
        return mv;
    }

    /**
     * 添加准备
     * @param sellerId
     * @return
     */
    @GetMapping("toAdd")
    //请求参数注解可以设置默认值，由名称以及默认value对应
    public ModelAndView addProductJump(HttpSession session){
        String sellerId=(String) session.getAttribute("sellerId");
        List<ProductCategory> productCategories=categoryService.findAll();
        List<Shop> shops = shopService.findAllBySellerId(sellerId);
        ModelAndView mv=new ModelAndView();
        mv.addObject("productCategories",productCategories);
        mv.addObject("shops",shops);
        mv.setViewName("product/add");
        return mv;
    }

    /**
     * 商品添加
     * @param imgUrl
     * @param
     * @param sellerId
     * @return
     */
    @PostMapping("/add")
    public ModelAndView addProduct(@RequestParam(value = "shopId") Integer shopId,
                                   @RequestParam(value = "name") String name,
                                   @RequestParam(value = "price") BigDecimal price,
                                   @RequestParam(value = "productStock") Integer productStock,
                                   @RequestParam(value = "imgUrl") MultipartFile imgUrl,
                                   @RequestParam(value = "type") String type
                                   ,HttpSession session ){
        ModelAndView mv=new ModelAndView();
        mv.addObject("url","/jingdong/seller/product/list");
        String sellerId=(String) session.getAttribute("sellerId");
        try {
            String url=UploadUtil.fileUpload(imgUrl,fileUploadConfig);
            Product product=new Product(name,0,price,null,productStock,url,type,shopId,sellerId);
            productService.save(product);
        }catch (Exception e){
            log.error("【商品添加】失败{}",e);
            mv.addObject("msg",e.getMessage());
            mv.setViewName("common/error");
        };
        mv.addObject("msg", ResultEnum.PRODUCT_ADD_SUCCESS.getMessage());
        mv.setViewName("common/success");
        return mv;
    }

    /**
     * 添加准备
     * @param sellerId
     * @return
     */
    @GetMapping("toUpdate")
    //请求参数注解可以设置默认值，由名称以及默认value对应
    public ModelAndView updateProductJump(@RequestParam(value = "productId") Integer productId,
                                          HttpSession session){
        Product product=productService.findOne(productId);
        List<ProductCategory> productCategories=categoryService.findAll();
        List<Shop> shops = shopService.findAllBySellerId((String) session.getAttribute("sellerId"));
        ModelAndView mv=new ModelAndView();
        mv.addObject("productCategories",productCategories);
        mv.addObject("shops",shops);
        mv.addObject("product",product);
        mv.setViewName("product/update");
        return mv;
    }

    /**
     * 商品更新
     * @param imgUrl
     * @param
     * @param sellerId
     * @return
     */
    @PostMapping("/update")
    public ModelAndView updateProduct(@RequestParam(value = "productId") Integer productId,
                                      @RequestParam(value = "shopId") Integer shopId,
                                      @RequestParam(value = "name") String name,
                                      @RequestParam(value = "price") BigDecimal price,
                                      @RequestParam(value = "oldPrice") BigDecimal oldPrice,
                                      @RequestParam(value = "productStock") Integer productStock,
                                      @RequestParam(value = "imgUrl") MultipartFile imgUrl,
                                      @RequestParam(value = "oldUrl") String oldUrl,
                                      @RequestParam(value = "type") String type,
                                      HttpSession session){
        ModelAndView mv=new ModelAndView();
        mv.addObject("url","/jingdong/seller/product/list");
        String sellerId=(String) session.getAttribute("sellerId");
        try {
            String url=UploadUtil.fileUpload(imgUrl,fileUploadConfig);
            if (url.equals("未更新文件")){
                url=oldUrl;
            }
            Product product;
            if (price.equals(oldPrice)){
                product=new Product(productId,name,0,price,productStock,url,type,shopId,sellerId);
            }else {
                product=new Product(productId,name,0,price,oldPrice,productStock,url,type,shopId,sellerId);
            }
            productService.save(product);
        }catch (Exception e){
            log.error("【商品更新】失败{}",e);
            mv.addObject("msg",e.getMessage());
            mv.setViewName("common/error");
        };
        mv.addObject("msg", ResultEnum.PRODUCT_UPDATE_SUCCESS.getMessage());
        mv.setViewName("common/success");
        return mv;
    }

    @GetMapping("/delete")
    //请求参数注解可以设置默认值，由名称以及默认value对应
    public ModelAndView deleteShop(@RequestParam(value = "productId") Integer productId){
        ModelAndView mv=new ModelAndView();
        mv.addObject("url","/jingdong/seller/product/list");
        try {
            productService.delete(productId);
        }catch (Exception e){
            log.error("【商品删除】失败{}",e);
            mv.addObject("msg",e.getMessage());
            mv.setViewName("common/error");
        };
        mv.addObject("msg", ResultEnum.PRODUCT_DELETE_SUCCESS.getMessage());
        mv.setViewName("common/success");
        return mv;
    }

}
