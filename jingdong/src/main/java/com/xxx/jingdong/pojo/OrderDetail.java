package com.xxx.jingdong.pojo;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Author；Aaron
 */
@Entity
@Table(name = "hanma_order_detail")
@Proxy(lazy = false)
@Data
public class OrderDetail {
    @Id
    private String detailId;
    private String orderNo;
    private Integer productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String imgUrl;
}
