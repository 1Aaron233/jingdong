package com.xxx.jingdong.service;

import com.xxx.jingdong.pojo.ShopCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Author；Aaron
 */
public interface ShopCategoryService {

    /**
     * 添加或更改
     * @param shopCategory
     * @return ShopCategory
     */
    ShopCategory save(ShopCategory shopCategory);

    /**
     * 根据id查询单条
     * @param categoryId
     * @return ShopCategory
     */
    ShopCategory findOne(Integer categoryId);

    /**
     * 查询全部数据
     * @return List<ShopCategory>
     */
    List<ShopCategory>findAll();

    /**
     * 根据查询全部数据
     * @return List<ShopCategory>
     */
    List<ShopCategory>findAllByStatus(Integer status);

    //以下为卖家端

    /**
     * 根据查询全部数据(卖家端)
     * @return Page<ShopCategory>
     */
    Page<ShopCategory> findAllByPage(Pageable pageable);


}
