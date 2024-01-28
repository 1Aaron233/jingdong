package com.xxx.jingdong.converter;

import com.alibaba.fastjson.JSONObject;
import com.xxx.jingdong.bo.OrderDTO;
import com.xxx.jingdong.form.OrderForm;
import com.xxx.jingdong.pojo.OrderDetail;
import lombok.extern.slf4j.Slf4j;

/**
 * Author；Aaron
 */
@Slf4j
public class OrderFormToOrderDTOConverter {
    public static OrderDTO converter(OrderForm orderForm){
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setUserId(orderForm.getUserId());
        orderDTO.setUserName(orderForm.getName());
        orderDTO.setUserPhone(orderForm.getPhone());
        orderDTO.setUserOpenid(orderForm.getOpenid());
        orderDTO.setUserAddress(orderForm.getAddress());
        orderDTO.setShopId(orderForm.getShopId());
        orderDTO.setShopName(orderForm.getShopName());
        //格式化json数据(转换为javaBean集合)
        orderDTO.setOrderDetailList(JSONObject.parseArray(orderForm.getProducts(), OrderDetail.class));
        return orderDTO;
    }
}
