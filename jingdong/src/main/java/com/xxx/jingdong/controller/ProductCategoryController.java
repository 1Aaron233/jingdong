package com.xxx.jingdong.controller;

import com.xxx.jingdong.pojo.ProductCategory;
import com.xxx.jingdong.domain.Result;
import com.xxx.jingdong.enums.ResultEnum;
import com.xxx.jingdong.enums.StatusEnum;
import com.xxx.jingdong.exception.SellException;
import com.xxx.jingdong.service.ProductCategoryService;
import com.xxx.jingdong.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService categoryService;

    /**
     * 获取特定店铺的商品分类
     * @return Result<List<ProductCategory>>
     */
    @GetMapping("/product/categorys/{shop_id}")
    public Result<List<ProductCategory>> getCategorys(@PathVariable("shop_id")Integer shopId){
        List<ProductCategory> categoryList=categoryService.findByStatus(shopId,StatusEnum.STATUS_YES.getCode());
        if(categoryList.isEmpty()){
            throw new SellException(ResultEnum.PRODUCTCATEGORY_NOT_EXIST);
        }
        return ResultUtil.success(categoryList);
    }

}
