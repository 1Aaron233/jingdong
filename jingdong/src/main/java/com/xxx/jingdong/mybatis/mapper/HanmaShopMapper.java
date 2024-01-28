package com.xxx.jingdong.mybatis.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.jingdong.pojo.Shop;
import org.springframework.stereotype.Repository;

/**
* @author Administrator
* @description 针对表【hanma_shop】的数据库操作Mapper
* @createDate 2023-12-28 15:16:45
* @Entity generator.domain.HanmaShop
*/
@Repository
public interface HanmaShopMapper extends BaseMapper<Shop> {

    //热门店铺包含字段
    List<Shop> findAllByNameContainingAndStatusAndState(@Param("name") String name, @Param("status") Integer status, @Param("state") Integer state);


    Shop findOneById(@Param("id") Integer id);
}




