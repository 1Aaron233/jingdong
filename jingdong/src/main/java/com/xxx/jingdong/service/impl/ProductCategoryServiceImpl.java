package com.xxx.jingdong.service.impl;

import com.xxx.jingdong.pojo.ProductCategory;
import com.xxx.jingdong.repository.ProductCategoryRepository;
import com.xxx.jingdong.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Author；Aaron
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory save(ProductCategory category) {
        return repository.save(category);
    }

    @Override
    public ProductCategory findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<ProductCategory> findByStatus(Integer shopId, Integer status) {
        return repository.findByShopIdAndStatus(shopId,status);
    }

    @Override
    public Page<ProductCategory> findAllByPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }
}
