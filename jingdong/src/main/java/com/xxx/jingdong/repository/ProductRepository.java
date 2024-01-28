package com.xxx.jingdong.repository;


import com.xxx.jingdong.pojo.Product;
import com.xxx.jingdong.pojo.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Author；Aaron
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {


    //获取店铺商品
    List<Product> findByShopIdAndTypeInOrderByIdDesc(Integer shopId,List<String> types);

    //获取店铺商品
    Page<Product> findAllBySellerId(Pageable pageable,String sellerId);

    //首页搜索
    Page<Product> findProductsByNameContainingIgnoreCase(Pageable pageable,String str);

}
