package com.xxx.jingdong.service.impl;


import com.xxx.jingdong.bo.CartDTO;
import com.xxx.jingdong.bo.ProductBo;
import com.xxx.jingdong.bo.ProductCategoryBo;
import com.xxx.jingdong.enums.ProductStatusEnum;
import com.xxx.jingdong.enums.ResultEnum;
import com.xxx.jingdong.enums.StatusEnum;
import com.xxx.jingdong.exception.SellException;
import com.xxx.jingdong.pojo.Product;
import com.xxx.jingdong.pojo.ProductCategory;
import com.xxx.jingdong.repository.ProductCategoryRepository;
import com.xxx.jingdong.repository.ProductRepository;
import com.xxx.jingdong.service.ProductCategoryService;
import com.xxx.jingdong.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author；Aaron
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findOne(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<ProductCategoryBo> findAllByShopId(Integer shopId) {
        //根据店铺获取素有元素
        List<ProductCategory> categories =categoryRepository.findByShopIdAndStatus(shopId, StatusEnum.STATUS_YES.getCode());
        if (categories.isEmpty()){
            throw new SellException(ResultEnum.PRODUCTCATEGORY_NOT_EXIST);
        }
        //获取店铺中分类集合
        List<String> categoryTypes =categories.stream().map(e->e.getType()).collect(Collectors.toList());
        //调用方法获取将该店铺具有分类下的所有商品
        List<Product> products =productRepository.findByShopIdAndTypeInOrderByIdDesc(shopId,categoryTypes);
        //将商品取出来，按照分类分好存入店铺下
        List<ProductCategoryBo> categoryBos=new ArrayList<>();
        for (ProductCategory category:categories){
            ProductCategoryBo categoryBo =new ProductCategoryBo();
            //将所属分类赋值
            BeanUtils.copyProperties(category,categoryBo);
            //将该分类下商品列表赋值
            List<ProductBo> productBos =new ArrayList<>();
            //循环遍历将相同分类下商品添加到同一列表
            for (Product product:products){
                if (category.getType().equals(product.getType())&&product.getStatus().equals(StatusEnum.STATUS_YES.getCode())){
                    ProductBo productBo =new ProductBo();
                    BeanUtils.copyProperties(product,productBo);
                    productBos.add(productBo);
                }
            }
            categoryBo.setProductBoList(productBos);
            //组合类添加到集合
            categoryBos.add(categoryBo);
        }
        return categoryBos;
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            //判断商品是否存在
            Product product=productRepository.findById(cartDTO.getProductId()).orElse(null);
            if(product == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //判断库存数量是否充足
            Integer leftStock = product.getProductStock()-cartDTO.getProductQuantity();
            if (leftStock < 0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            //更新库存
            product.setProductStock(leftStock);
            productRepository.save(product);
        }
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            //判断商品是否存在
            Product product=productRepository.findById(cartDTO.getProductId()).orElse(null);
            if(product == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //判断库存数量是否充足
            Integer leftStock = product.getProductStock()+cartDTO.getProductQuantity();
            if (leftStock < 0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            //更新库存
            product.setProductStock(leftStock);
            productRepository.save(product);
        }
    }

    //首页搜索
    @Override
    public Page<Product> findProductsByName(Pageable pageable,String str){
        return productRepository.findProductsByNameContainingIgnoreCase(pageable,str);
    }

    @Override
    public Page<Product> findAllByPage(Pageable pageable, String SellerId) {
        return productRepository.findAllBySellerId(pageable,SellerId);
    }

    @Override
    public void delete(Integer productId) {
        productRepository.deleteById(productId);
    }

    //商品上架
    @Override
    public Product productStatusUp(Integer productId) {
        Product product=productRepository.getReferenceById(productId);
        product.setStatus(ProductStatusEnum.STATUS_YES.getCode());
        return productRepository.save(product);
    }

    //商品下架
    @Override
    public Product productStatusDrop(Integer productId) {
        Product product=productRepository.getReferenceById(productId);
        product.setStatus(ProductStatusEnum.STATUS_NO.getCode());
        return productRepository.save(product);
    }


}
