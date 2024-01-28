package com.xxx.jingdong.controller;

import com.xxx.jingdong.pojo.Product;
import com.xxx.jingdong.pojo.Shop;
import com.xxx.jingdong.domain.Result;
import com.xxx.jingdong.enums.ResultEnum;
import com.xxx.jingdong.enums.StateEnum;
import com.xxx.jingdong.exception.SellException;
import com.xxx.jingdong.service.ShopService;
import com.xxx.jingdong.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ShopController {

    @Autowired
    private ShopService shopService;

    /**
     * 获取所有的热门店铺
     * @param start
     * @param page
     * @return Result<List<Shop>>
     */
    @GetMapping("/hotShops")
    public Result<List<Shop>> getHotShops(@RequestParam(name = "start",defaultValue = "1") Integer start,
                                          @RequestParam(name = "size",defaultValue = "10") Integer page){
        //注意需要注意查询的数据的第一页，start是从0开始的
        start =start<=1 ? 0 : start - 1;
        Pageable pageable=PageRequest.of(start,page);
        Page<Shop> shopPage=shopService.findAllByState(pageable, StateEnum.STATE_YES.getCode());
        if(shopPage.getContent().isEmpty()){
            throw new SellException(ResultEnum.SHOP_NOT_EXIST);
        }
        return ResultUtil.success(shopPage.getContent());
    }

    /**
     * 获取单个店铺
     * @param id
     * @return Result<Shop>
     */
    @GetMapping("/shop/{id}")
    public Result<Shop> getShop(@PathVariable("id") Integer id){
        Shop shop=shopService.findOne(id);
        if(shop == null){
            throw new SellException(ResultEnum.SHOP_NOT_EXIST);
        }
        return ResultUtil.success(shop);
    }

    /**
     * 店铺搜索
     * @param page
     * @param size
     * @param shopName
     * @return
     */
    @GetMapping("/shop/find")
    //请求参数注解可以设置默认值，由名称以及默认value对应
    public Result<Page<Shop>> getOrders(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                           @RequestParam(value = "size",defaultValue = "10") Integer size,
                                           @RequestParam("shopName") String shopName){
        PageRequest request=PageRequest.of(page-1,size);
        Page<Shop> shops=shopService.findShopsByName(request,shopName);
        return ResultUtil.success(shops);
    }

}
