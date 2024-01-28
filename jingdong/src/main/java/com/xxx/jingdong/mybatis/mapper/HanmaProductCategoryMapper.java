package com.xxx.jingdong.mybatis.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.xxx.jingdong.pojo.ProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author Administrator
* @description 针对表【hanma_product_category】的数据库操作Mapper
* @createDate 2023-12-28 17:58:18
* @Entity generator.domain.HanmaProductCategory
*/
@Repository
public interface HanmaProductCategoryMapper extends BaseMapper<ProductCategory> {
    List<ProductCategory> findAllByShopIdAndStatus(@Param("shopId") Integer shopId, @Param("status") Integer status);
}




