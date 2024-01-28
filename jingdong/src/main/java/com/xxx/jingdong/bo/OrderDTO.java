package com.xxx.jingdong.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xxx.jingdong.enums.OrderStatusEnum;
import com.xxx.jingdong.enums.PayStatusEnum;
import com.xxx.jingdong.pojo.OrderDetail;
import com.xxx.jingdong.utils.EnumUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Author；Aaron
 */
//用于订单与订单详情的组装
@Data
public class OrderDTO {
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
    private Integer paytime;
    private Integer orderStatus;
    private Integer payStatus;
    List<OrderDetail> orderDetailList;
//    /*创建时间*/
//    @JsonSerialize(using= Date2LongSerializer.class)
//    private Date creatTime;
//    /*更新时间*/
//    @JsonSerialize(using = Date2LongSerializer.class)
//    private Date updateTime;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
