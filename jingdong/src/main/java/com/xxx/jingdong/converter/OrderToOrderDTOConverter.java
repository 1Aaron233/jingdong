package com.xxx.jingdong.converter;

import com.xxx.jingdong.bo.OrderDTO;
import com.xxx.jingdong.pojo.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author；Aaron
 */
@Slf4j
public class OrderToOrderDTOConverter {
    //转单条数据
    public static OrderDTO converter(Order order){
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(order,orderDTO);
        return orderDTO;
    }

    //转多条数据
    public static List<OrderDTO> converter(List<Order> orders){
        return orders.stream().map(e->converter(e)).collect(Collectors.toList());
    }
}
