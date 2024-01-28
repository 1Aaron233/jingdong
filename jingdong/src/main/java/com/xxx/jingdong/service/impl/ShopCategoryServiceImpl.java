package com.xxx.jingdong.service.impl;

import com.xxx.jingdong.pojo.ShopCategory;
import com.xxx.jingdong.repository.ShopCategoryRepository;
import com.xxx.jingdong.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author；Aaron
 */
/*
此类仅可在平台方后台进行操作
* **/
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryRepository repository;

    @Override
    public ShopCategory save(ShopCategory shopCategory) {
        return repository.save(shopCategory);
    }

    @Override
    public ShopCategory findOne(Integer categoryId) {
        return repository.findById(categoryId).orElse(null);
    }

    @Override
    public List<ShopCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ShopCategory> findAllByStatus(Integer status) {
        return repository.findAllByStatus(status);
    }

    @Override
    public Page<ShopCategory> findAllByPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

}
