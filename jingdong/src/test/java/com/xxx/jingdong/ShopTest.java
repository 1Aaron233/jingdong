package com.xxx.jingdong;

import com.xxx.jingdong.pojo.Shop;
import com.xxx.jingdong.enums.StateEnum;
import com.xxx.jingdong.repository.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Author；Aaron
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ShopTest {
    @Autowired
    private ShopRepository repository;

    /**
     * 分页倒序查询
     */
    @Test
    public void findAll(){
        Pageable pageable= PageRequest.of(0,10, Sort.by(Sort.Direction.DESC,"id"));
        Page<Shop> shopList=repository.findAll(pageable);
        log.info("result={}",shopList.getContent());
    }

    /**
     * 热门店铺分页倒序查询
     */
    @Test
    public void findAllByState(){
        Pageable pageable= PageRequest.of(0,10);
        Page<Shop> shopList=repository.findAllByStateOrderByIdDesc(pageable, StateEnum.STATE_YES.getCode());
        log.info("result={}",shopList.getContent());
    }
}
