package com.xxx.jingdong.repository;

import com.xxx.jingdong.pojo.Product;
import com.xxx.jingdong.pojo.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author；Aaron
 */
@Repository
public interface ShopRepository extends JpaRepository<Shop,Integer> {
    Page<Shop> findAllByStateOrderByIdDesc(Pageable pageable,Integer state);
    //首页搜索
    Page<Shop> findShopsByNameContainingIgnoreCase(Pageable pageable, String str);

    //以下为卖家端
    List<Shop> findAllBySellerId(String sellerId);
    Page<Shop> findAllBySellerId(Pageable pageable,String sellerId);

}
