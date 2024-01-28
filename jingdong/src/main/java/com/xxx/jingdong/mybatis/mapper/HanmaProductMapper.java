package com.xxx.jingdong.mybatis.mapper;
import java.util.Collection;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.jingdong.pojo.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
* @author Administrator
* @description 针对表【hanma_product】的数据库操作Mapper
* @createDate 2023-12-28 15:43:35
* @Entity generator.domain.HanmaProduct
*/
@Repository
public interface HanmaProductMapper extends BaseMapper<Product> {

    //超市里面含字段,且商品里面不含
    List<Product> findAllByShopIdAndStatusAndState(@Param("shopId") Integer shopId, @Param("status") Integer status, @Param("state") Integer state);

    //超市里面含字段，且商品里面也含||超市里面不含字段，商品里面含
    List<Product> findAllByNameContainingAndStatusAndShopIdAndState(@Param("name") String name, @Param("status") Integer status, @Param("shopId") Integer shopId, @Param("state") Integer state);

    //实现商品信息和商品分类的组装
    List<Product> findByShopIdAndTypeInOrderByIdDesc(@Param("shopId") Integer shopId, @Param("typeList") Collection<String> typeList);
}




