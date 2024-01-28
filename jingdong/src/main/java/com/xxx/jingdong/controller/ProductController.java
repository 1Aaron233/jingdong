package com.xxx.jingdong.controller;

import com.xxx.jingdong.bo.OrderDTO;
import com.xxx.jingdong.bo.ProductCategoryBo;
import com.xxx.jingdong.domain.Result;
import com.xxx.jingdong.enums.ResultEnum;
import com.xxx.jingdong.exception.SellException;
import com.xxx.jingdong.pojo.Product;
import com.xxx.jingdong.pojo.Shop;
import com.xxx.jingdong.service.ProductService;
import com.xxx.jingdong.service.ShopService;
import com.xxx.jingdong.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;

    /**
     * 特定店铺下所有商品分类加信息数据组装
     * @param shopId
     * @return Result<List<ProductCategoryBo>>
     */
    @GetMapping("/product/{shop_id}")
    public Result<List<ProductCategoryBo>> getCategorys(@PathVariable("shop_id")Integer shopId){
        //验证店铺是否存在
        Shop shop =shopService.findOne(shopId);
        if(shop == null){
            throw new SellException(ResultEnum.SHOP_NOT_EXIST);
        }
        //处理数据
        List<ProductCategoryBo> productCategoryBos=productService.findAllByShopId(shopId);
        if(productCategoryBos.isEmpty()){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        //响应数据
        return ResultUtil.success(productCategoryBos);
    }

    /**
     * 首页搜索
     * @param page
     * @param size
     * @param productName
     * @return
     */
    @GetMapping("/product/find")
    //请求参数注解可以设置默认值，由名称以及默认value对应
    public Result<Page<Product>> getOrders(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                  @RequestParam(value = "size",defaultValue = "10") Integer size,
                                  @RequestParam("productName") String productName){
        PageRequest request=PageRequest.of(page-1,size);
        Page<Product> products=productService.findProductsByName(request,productName);
        return ResultUtil.success(products);
    }

//    /**
//     * 获取热门
//     * @param hotNameStr
//     * @return
//     */
//    @PostMapping("/hot")
//    public Result<List<HotProductDTO>> hot(@RequestParam(value = "hotNameStr") String hotNameStr){
//        List<HotProductDTO> result = productService.hotproductAssemble(hotNameStr);
//        return ResultUtil.success(result);
//    }

}
