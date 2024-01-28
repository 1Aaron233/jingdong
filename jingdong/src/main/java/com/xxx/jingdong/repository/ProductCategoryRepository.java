package com.xxx.jingdong.repository;



import com.xxx.jingdong.pojo.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author；Aaron
 */
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
    //根据店铺id获取商品分类
    List<ProductCategory> findByShopIdAndStatus(Integer shopId,Integer status);

}
