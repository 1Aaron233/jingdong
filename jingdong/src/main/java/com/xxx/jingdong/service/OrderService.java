package com.xxx.jingdong.service;

import com.xxx.jingdong.bo.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Author；Aaron
 */
public interface OrderService {
    /**
     *创建订单
     * @param orderDTO
     * @return OrderDTO
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 查询单个订单
     * @param orderNo
     * @return OrderDTO
     */
    OrderDTO findOne(String orderNo);

    /**
     * 查询订单列表
     * @param userOpenid
     * @param pageable
     * @return Page<OrderDTO>
     */
    Page<OrderDTO> findList(String userOpenid,Pageable pageable);

    /**
     * 取消订单
     * @param orderNo
     * @return OrderDTO
     */
    OrderDTO cancel(String orderNo);

    /**
     * 完结订单
     * @param orderNo
     * @return OrderDTO
     */
    OrderDTO finish(String orderNo);

    /**
     * 支付订单
     * @param orderNo
     * @return OrderDTO
     */
    OrderDTO paid(String orderNo);

    //下方为卖家端

    /**
     * 查询订单数据列表
     * @param pageable
     * @return
     */
    Page<OrderDTO> findAll(Pageable pageable,String sellerId);

}
