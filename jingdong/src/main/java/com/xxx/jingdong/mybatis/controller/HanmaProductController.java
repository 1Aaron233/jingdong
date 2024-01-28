package com.xxx.jingdong.mybatis.controller;

import com.xxx.jingdong.bo.HotProductDTO;
import com.xxx.jingdong.bo.ProductCategoryBo;
import com.xxx.jingdong.domain.Result;
import com.xxx.jingdong.enums.ResultEnum;
import com.xxx.jingdong.exception.SellException;
import com.xxx.jingdong.mybatis.service.HanmaProductService;
import com.xxx.jingdong.mybatis.service.HanmaShopService;
import com.xxx.jingdong.pojo.Product;
import com.xxx.jingdong.pojo.Shop;
import com.xxx.jingdong.service.ProductService;
import com.xxx.jingdong.service.ShopService;
import com.xxx.jingdong.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mybatis")
public class HanmaProductController {

    @Autowired
    private HanmaProductService productService;
    @Autowired
    private HanmaShopService shopService;

    /**
     * 热门商品(数据组装，作业)
     * @param name
     * @return Result<List<HotProductDTO>>
     */
    @GetMapping("/hotProduct")
    public Result<List<HotProductDTO>> getCategorys(@RequestParam("name")String name){
        List<HotProductDTO> hotProductDTOs=productService.findAll(name);
        if(hotProductDTOs.isEmpty()){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        //响应数据
        return ResultUtil.success(hotProductDTOs);
    }

    /**
     * 特定店铺下所有商品分类加信息数据组装(使用mybatis和mybatis-plus)
     * @param shopId
     * @return Result<List<ProductCategoryBo>>
     */
    @GetMapping("/product/{shop_id}")
    public Result<List<ProductCategoryBo>> getCategorys(@PathVariable("shop_id")Integer shopId){
        //验证店铺是否存在
        Shop shop =shopService.findOneById(shopId);
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

}
