package com.xxx.jingdong.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.jingdong.pojo.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【hanma_shop】的数据库操作Service
* @createDate 2023-12-28 15:16:45
*/
public interface HanmaShopService extends IService<Shop> {

    Shop findOneById(@Param("id") Integer id);
}
