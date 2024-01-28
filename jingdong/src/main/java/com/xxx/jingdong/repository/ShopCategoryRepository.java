package com.xxx.jingdong.repository;

import com.xxx.jingdong.pojo.ShopCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author；Aaron
 */
@Repository
public interface ShopCategoryRepository extends JpaRepository<ShopCategory,Integer> {
    //获取店铺分类
   List<ShopCategory> findAllByStatus(Integer status);
   //获取店铺分类（分页）
   Page<ShopCategory> findAll(Pageable pageable);
}
