package com.xxx.jingdong.controller;

import com.xxx.jingdong.pojo.ShopCategory;
import com.xxx.jingdong.domain.Result;
import com.xxx.jingdong.enums.ResultEnum;
import com.xxx.jingdong.enums.StatusEnum;
import com.xxx.jingdong.exception.SellException;
import com.xxx.jingdong.service.ShopCategoryService;
import com.xxx.jingdong.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ShopCategoryController {

    @Autowired
    private ShopCategoryService categoryService;

    /**
     * 获取所有的店铺分类
     * @return Result<ShopCategory>
     */
    @GetMapping("/categorys")
    public Result<ShopCategory> getCategorys(){
        List<ShopCategory> categoryList=categoryService.findAllByStatus(StatusEnum.STATUS_YES.getCode());
        if(categoryList.isEmpty()){
            throw new SellException(ResultEnum.SHOPCATEORY_NOT_EXIST);
        }
        return ResultUtil.success(categoryList);
    }

}
