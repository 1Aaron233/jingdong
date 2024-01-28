package com.xxx.jingdong.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.jingdong.enums.StateEnum;
import com.xxx.jingdong.enums.StatusEnum;
import com.xxx.jingdong.mybatis.mapper.HanmaShopMapper;
import com.xxx.jingdong.mybatis.service.HanmaShopService;
import com.xxx.jingdong.pojo.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【hanma_shop】的数据库操作Service实现
* @createDate 2023-12-28 15:16:45
*/
@Service
public class HanmaShopServiceImpl extends ServiceImpl<HanmaShopMapper, Shop>
    implements HanmaShopService {
    @Autowired
    private HanmaShopMapper shopMapper;

    @Override
    public Shop findOneById(Integer id) {
        return shopMapper.findOneById(id);
    }
}




