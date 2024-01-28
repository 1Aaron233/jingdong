package com.xxx.jingdong.service;

import com.xxx.jingdong.bo.CartDTO;
import com.xxx.jingdong.bo.ProductCategoryBo;
import com.xxx.jingdong.pojo.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

/**
 * Author；Aaron
 */
public interface ProductService {
    /**
     * 添加或更改
     * @param product
     * @return Product
     */
    Product save(Product product);

    /**
     * 根据id查询单条
     * @param id
     * @return Product
     */
    Product findOne(Integer id);

    /**
     * 分页查询全部数据
     * @param pageable
     * @return Page<Product>
     */
    Page<Product> findAll(Pageable pageable);

    /**
     * 特定店铺下所有商品分类加信息数据组装
     * @param shopId
     * @return List<ProductCategoryBo>
     */
    List<ProductCategoryBo> findAllByShopId(Integer shopId);

    /**
     * 减库存
     * @param cartDTOList
     */
    void decreaseStock(List<CartDTO> cartDTOList);

    /**
     * 增库存
     * @param cartDTOList
     */
    void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 首页搜索（★）
     * @param pageable
     * @param str
     * @return
     */
    Page<Product> findProductsByName(Pageable pageable,String str);

    //以下为卖家端
    /**
     * 分页查询全部数据
     * @param pageable
     * @return Page<Product>
     */
    Page<Product> findAllByPage(Pageable pageable,String SellerId);

    /**
     * 删除商品
     * @param productId
     */
    void delete(Integer productId);

    //商品下架(改)
    Product productStatusDrop(Integer productId);

    //商品上架(改)
    Product productStatusUp(Integer productId);

}
