package com.xxx.jingdong.bo;

import com.xxx.jingdong.pojo.Product;
import lombok.Data;

import java.util.List;

/**
 * Author；Aaron
 */
//用于店铺和商品分类与订单详情的组装
@Data
public class HotProductDTO {
    private String shopName;
    private List<Product> products;
}
