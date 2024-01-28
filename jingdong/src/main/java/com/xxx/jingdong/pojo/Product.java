package com.xxx.jingdong.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xxx.jingdong.enums.ProductStatusEnum;
import com.xxx.jingdong.enums.StateEnum;
import com.xxx.jingdong.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Author；Aaron
 */
@Entity
@Table(name= "hanma_product")
@Data
@Proxy(lazy = false)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer sales;
    private BigDecimal price;
    @Column(name = "old_price")
    private BigDecimal oldPrice;
    @Column(name = "product_stock")
    private Integer productStock;
    @Column(name = "img_url")
    private String imgUrl;
    private String type;
    @Column(name = "shop_id")
    private Integer shopId;
    @Column(name= "seller_id")
    private String sellerId;
    private Integer status = ProductStatusEnum.STATUS_YES.getCode();
    private Integer state = StateEnum.STATE_YES.getCode();

    public Product() {

    }

    public Product(String name, Integer sales, BigDecimal price, BigDecimal oldPrice, Integer productStock, String imgUrl, String type, Integer shopId, String sellerId) {
        this.name = name;
        this.sales = sales;
        this.price = price;
        this.oldPrice = oldPrice;
        this.productStock = productStock;
        this.imgUrl = imgUrl;
        this.type = type;
        this.shopId = shopId;
        this.sellerId = sellerId;
    }

    public Product( Integer id, String name, Integer sales, BigDecimal price, Integer productStock, String imgUrl, String type, Integer shopId, String sellerId) {
        this.id = id;
        this.name = name;
        this.sales = sales;
        this.price = price;
        this.productStock = productStock;
        this.imgUrl = imgUrl;
        this.type = type;
        this.shopId = shopId;
        this.sellerId = sellerId;
    }

    public Product( Integer id, String name, Integer sales, BigDecimal price,BigDecimal oldPrice,Integer productStock, String imgUrl, String type, Integer shopId, String sellerId) {
        this.id = id;
        this.name = name;
        this.sales = sales;
        this.oldPrice = oldPrice;
        this.price = price;
        this.productStock = productStock;
        this.imgUrl = imgUrl;
        this.type = type;
        this.shopId = shopId;
        this.sellerId = sellerId;
    }



    @JsonIgnore
    public ProductStatusEnum getStatusEnum(){
        return EnumUtil.getByCode(status, ProductStatusEnum.class);
    }

    @JsonIgnore
    public StateEnum getStateEnum(){
        return EnumUtil.getByCode(state, StateEnum.class);
    }

}

