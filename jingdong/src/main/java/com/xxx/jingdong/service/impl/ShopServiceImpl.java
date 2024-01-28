package com.xxx.jingdong.service.impl;


import com.xxx.jingdong.pojo.Product;
import com.xxx.jingdong.pojo.Shop;
import com.xxx.jingdong.repository.ShopRepository;
import com.xxx.jingdong.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Author；Aaron
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository repository;

    @Override
    public Shop save(Shop shop) {
        return repository.save(shop);
    }

    @Override
    public Shop findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Page<Shop> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Shop>findAllByState(Pageable pageable, Integer state) {
        return repository.findAllByStateOrderByIdDesc(pageable,state);
    }

    //首页搜索
    @Override
    public Page<Shop> findShopsByName(Pageable pageable, String str){
        return repository.findShopsByNameContainingIgnoreCase(pageable,str);
    }

    @Override
    public List<Shop> findAllBySellerId(String sellerId) {
        return repository.findAllBySellerId(sellerId);
    }

    @Override
    public Page<Shop> findAllByPage(Pageable pageable,String sellerId) {
        return repository.findAllBySellerId(pageable,sellerId);
    }
}
