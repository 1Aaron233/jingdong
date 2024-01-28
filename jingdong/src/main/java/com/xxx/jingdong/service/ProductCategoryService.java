package com.xxx.jingdong.service;


import com.xxx.jingdong.pojo.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Author；Aaron
 */
public interface ProductCategoryService {
   /**
   * 保存或修改
   * @param category
   * @return ProductCategory
   */
   ProductCategory save(ProductCategory category);

    /**
     * 根据id查询单条
     * @param id
     * @return ProductCategory
     */
   ProductCategory findOne(Integer id);

    /**
     * 根据状态查询分类
     * @param shopId
     * @param status
     * @return List<ProductCategory>
     */
   List<ProductCategory> findByStatus(Integer shopId,Integer status);

   //以下为卖家端
    /**
     * 查询分类(分页)
     * @return Page<ProductCategory>
     */
    Page<ProductCategory> findAllByPage(Pageable pageable);

    //以下为卖家端
    /**
     * 查询分类(不分页)
     * @return Page<ProductCategory>
     */
    List<ProductCategory> findAll();
}
