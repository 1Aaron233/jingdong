package com.xxx.jingdong.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.jingdong.bo.HotProductDTO;
import com.xxx.jingdong.bo.ProductBo;
import com.xxx.jingdong.bo.ProductCategoryBo;
import com.xxx.jingdong.enums.ResultEnum;
import com.xxx.jingdong.enums.StateEnum;
import com.xxx.jingdong.enums.StatusEnum;
import com.xxx.jingdong.exception.SellException;
import com.xxx.jingdong.mybatis.mapper.HanmaProductCategoryMapper;
import com.xxx.jingdong.mybatis.mapper.HanmaProductMapper;
import com.xxx.jingdong.mybatis.mapper.HanmaShopMapper;
import com.xxx.jingdong.mybatis.service.HanmaProductService;
import com.xxx.jingdong.pojo.Product;
import com.xxx.jingdong.pojo.ProductCategory;
import com.xxx.jingdong.pojo.Shop;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【hanma_product】的数据库操作Service实现
* @createDate 2023-12-28 15:43:35
*/
@Service
public class HanmaProductServiceImpl extends ServiceImpl<HanmaProductMapper,Product>
    implements HanmaProductService {
    @Autowired
    private HanmaProductMapper productMapper;
    @Autowired
    private HanmaShopMapper shopMapper;
    @Autowired
    private HanmaProductCategoryMapper productCategoryMapper;


    @Override
    public List<HotProductDTO> findAll(String name) {
        //创建组装函数容器
        List<HotProductDTO> hotProductDTOs=new ArrayList<>();
        /*
       热门店铺-包含字段
          -有热门商品-存热门商品
          -没有热门商品-存全部商品
         */
        List<Shop> shops=shopMapper.findAllByNameContainingAndStatusAndState(name,StatusEnum.STATUS_YES.getCode(), StateEnum.STATE_YES.getCode());
        HotProductDTO hotProductDTO;
        for (Shop shop:shops){
            hotProductDTO=new HotProductDTO();
            hotProductDTO.setShopName(shop.getName());
            List<Product> products =productMapper.findAllByNameContainingAndStatusAndShopIdAndState(name,StatusEnum.STATUS_YES.getCode(),shop.getId(), StateEnum.STATE_YES.getCode());
            if (products.isEmpty()){
                products =productMapper.findAllByShopIdAndStatusAndState(shop.getId(),StatusEnum.STATUS_YES.getCode(), StateEnum.STATE_YES.getCode());
            }
            hotProductDTO.setProducts(products);
            hotProductDTOs.add(hotProductDTO);
        }
        System.out.println(hotProductDTOs);
         /*
       热门店铺-不包含字段
          -有热门商品-存热门商品
          -没有热门商品-不存
         */
        QueryWrapper<Shop> queryWrapper = new QueryWrapper<>();
        // 设置 not like 条件，表示不包含指定字段值
        queryWrapper.notLike("name",name)
                    .eq("status",StatusEnum.STATUS_YES.getCode())
                    .eq("state",StateEnum.STATE_YES.getCode());
        shops=shopMapper.selectList(queryWrapper);
        for (Shop shop:shops){
            hotProductDTO=new HotProductDTO();
            List<Product> products =productMapper.findAllByNameContainingAndStatusAndShopIdAndState(name,StatusEnum.STATUS_YES.getCode(),shop.getId(), StateEnum.STATE_YES.getCode());
            if (!products.isEmpty()){
                hotProductDTO.setShopName(shop.getName());
                hotProductDTO.setProducts(products);
                hotProductDTOs.add(hotProductDTO);
            }
        }
        System.out.println(hotProductDTOs);
        /*
        非热门店铺
        -有热门商品-存热门商品
        -没有热门商品-不存
         */
        QueryWrapper<Shop> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("status",StatusEnum.STATUS_YES.getCode())
                     .eq("state",StateEnum.STATE_NO.getCode());
        shops=shopMapper.selectList(queryWrapper1);
        for (Shop shop:shops){
            hotProductDTO=new HotProductDTO();
            List<Product> products =productMapper.findAllByNameContainingAndStatusAndShopIdAndState(name,StatusEnum.STATUS_YES.getCode(),shop.getId(), StateEnum.STATE_YES.getCode());
            if (!products.isEmpty()){
                hotProductDTO.setShopName(shop.getName());
                hotProductDTO.setProducts(products);
                hotProductDTOs.add(hotProductDTO);
            }
        }
        System.out.println(hotProductDTOs);
        return hotProductDTOs;
    }

    @Override
    public List<ProductCategoryBo> findAllByShopId(Integer shopId) {
        //根据店铺获取素有元素
        List<ProductCategory> categories =productCategoryMapper.findAllByShopIdAndStatus(shopId, StatusEnum.STATUS_YES.getCode());
        if (categories.isEmpty()){
            throw new SellException(ResultEnum.PRODUCTCATEGORY_NOT_EXIST);
        }
        //获取店铺中分类集合
        List<String> categoryTypes =categories.stream().map(e->e.getType()).collect(Collectors.toList());
        //调用方法获取将该店铺具有分类下的所有商品
        List<Product> products =productMapper.findByShopIdAndTypeInOrderByIdDesc(shopId,categoryTypes);
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

}




