package com.xxx.jingdong.pojo;

import com.xxx.jingdong.enums.OrderStatusEnum;
import com.xxx.jingdong.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Author；Aaron
 */
@Entity
@Table(name = "hanma_order")
@Proxy(lazy = false)
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String orderNo;
    private String sellerId;
    private Integer userId;
    private String userName;
    private String userPhone;
    private String userAddress;
    private String userOpenid;
    private Integer shopId;
    private String shopName;
    private BigDecimal orderAmount;
    private Integer payTime;
    private Integer payStatus= PayStatusEnum.WAIT.getCode();
    private Integer orderStatus= OrderStatusEnum.NEW.getCode();

}
