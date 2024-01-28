package com.xxx.jingdong.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.jingdong.bo.HotProductDTO;
import com.xxx.jingdong.bo.ProductCategoryBo;
import com.xxx.jingdong.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【hanma_product】的数据库操作Service
* @createDate 2023-12-28 15:43:35
*/
public interface HanmaProductService extends IService<Product> {
    /**
     * 查询热门商品（数据组装）
     * @param name
     * @return
     */
    List<HotProductDTO> findAll(@Param("name") String name);

    /**
     * 实现商品分类和商品信息的组装
     * @param shopId
     * @return
     */
    List<ProductCategoryBo> findAllByShopId(Integer shopId);
}
