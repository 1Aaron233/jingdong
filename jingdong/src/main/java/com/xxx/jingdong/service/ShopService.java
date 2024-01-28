package com.xxx.jingdong.service;

import com.xxx.jingdong.pojo.Product;
import com.xxx.jingdong.pojo.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * Author；Aaron
 */
public interface ShopService {

    /**
     * 添加或更改
     * @param shop
     * @return ShopCategory
     */
    Shop save(Shop shop);

    /**
     * 根据id查询单条
     * @param id
     * @return ShopCategory
     */
    Shop findOne(Integer id);

    /**
     * 查询全部数据
     * @return Page<Shop>
     */
    Page<Shop> findAll(Pageable pageable);

    /**
     * 查询全部热门数据
     * @return Page<Shop>
     */
    Page<Shop>findAllByState(Pageable pageable,Integer state);

    /**
     * 首页搜索（★）
     * @param pageable
     * @param str
     * @return
     */
    Page<Shop> findShopsByName(Pageable pageable, String str);

    //以下为卖家端
    /**
     * 查询全部数据(分页)
     * @return Page<Shop>
     */
    Page<Shop>findAllByPage(Pageable pageable,String sellerId);

    /**
     * 查询全部数据(分页)
     * @return Page<Shop>
     */
    List<Shop> findAllBySellerId(String sellerId) ;


}