package com.xxx.jingdong.service.impl;

import com.xxx.jingdong.bo.CartDTO;
import com.xxx.jingdong.bo.OrderDTO;
import com.xxx.jingdong.converter.OrderToOrderDTOConverter;
import com.xxx.jingdong.enums.OrderStatusEnum;
import com.xxx.jingdong.enums.PayStatusEnum;
import com.xxx.jingdong.enums.ResultEnum;
import com.xxx.jingdong.exception.SellException;
import com.xxx.jingdong.pojo.Order;
import com.xxx.jingdong.pojo.OrderDetail;
import com.xxx.jingdong.pojo.Product;
import com.xxx.jingdong.repository.OrderDetailRepository;
import com.xxx.jingdong.repository.OrderRepository;
import com.xxx.jingdong.repository.ProductRepository;
import com.xxx.jingdong.service.OrderService;
import com.xxx.jingdong.service.ProductService;
import com.xxx.jingdong.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author；Aaron
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        //设置订单号
        String orderNo= KeyUtil.getUniquekey();
        //计算总价
        BigDecimal orderAmount=new BigDecimal(BigInteger.ZERO);
        //将DTO中订单详情如何并计算总价填入订单
        for (OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            //判断商品是否存在
            Product product=productRepository.findById(orderDetail.getProductId()).orElse(null);
            if (product ==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //开始计算(循环增加)
            //java.math.BigInteger.multiply(BigInteger val) 返回一个BigInteger，其值是（this * val）
            orderAmount=product.getPrice().multiply(new BigDecimal(orderDetail.getQuantity())).add(orderAmount);
            //订单详情入库
            orderDetail.setDetailId(KeyUtil.getUniquekey());
            orderDetail.setOrderNo(orderNo);
            BeanUtils.copyProperties(product,orderDetail);
            orderDetailRepository.save(orderDetail);
        }
        //补齐orderDTO
//        orderDTO.setSellerId("111");
        orderDTO.setOrderAmount(orderAmount);
        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());
        //保存订单入库
        Order order =new Order();
        //copyProperties方法的意思是把相同的属性赋值给另外一个类。
        BeanUtils.copyProperties(orderDTO,order);
        order.setOrderNo(orderNo);
        orderRepository.save(order);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderNo) {
        //查询订单信息
        Order order=orderRepository.findByOrderNo(orderNo);
        if (order == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        OrderDTO orderDTO=converter(order);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String userOpenid, Pageable pageable) {
        Page<Order> orders=orderRepository.findByUserOpenid(userOpenid, pageable);
        if (orders == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //组装
        List<OrderDTO> orderDTOs = orders.stream().map(e->converter(e)).collect(Collectors.toList());
        return new PageImpl<OrderDTO>(orderDTOs,pageable,orders.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(String orderNo) {
        //查询订单是否存在，TODO 后期自己判断是否是当前用户的
        Order order=orderRepository.findByOrderNo(orderNo);
        if (order == null ){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //判断订单状态
        if (!order.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        order.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        Order updateResult =orderRepository.save(order);
        if (updateResult == null){
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返回库存
        OrderDTO orderDTO=converter(order);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finish(String orderNo) {
        //查询订单是否存在，TODO 后期自己判断是否是当前用户的
        Order order=orderRepository.findByOrderNo(orderNo);
        if (order == null ){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //判断订单状态-没支付报错
        if (order.getOrderStatus().equals(OrderStatusEnum.FINISHED.getCode())){
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        order.setOrderStatus(OrderStatusEnum.SEND_YES.getCode());
        Order updateResult =orderRepository.save(order);
        if (updateResult == null) {
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return converter(order);
    }

    @Override
    public OrderDTO paid(String orderNo) {
        //查询订单是否存在，TODO 后期自己判断是否是当前用户的
        Order order=orderRepository.findByOrderNo(orderNo);
        if (order == null ){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //判断订单状态
        if (order.getOrderStatus().equals(OrderStatusEnum.FINISHED.getCode())||order.getOrderStatus().equals(OrderStatusEnum.CANCEL.getCode())){
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!order.getOrderStatus().equals(PayStatusEnum.WAIT.getCode())){
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改订单状态
        order.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        order.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        Order updateResult =orderRepository.save(order);
        if (updateResult == null) {
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return converter(order);
    }

    //卖家端
    //查询显示
    @Override
    public Page<OrderDTO> findAll(Pageable pageable,String sellerId) {
        Page<Order> orderPage=orderRepository.findBySellerId(pageable,sellerId);
        List<OrderDTO> orderDTOS= OrderToOrderDTOConverter.converter(orderPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOS,pageable,orderPage.getTotalElements());
    }


    private OrderDTO converter(Order order){
        //查看订单详情
        List<OrderDetail> orderDetailList=orderDetailRepository.findByOrderNo(order.getOrderNo());
        if (orderDetailList.isEmpty()){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        //组装orderDTO数据
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(order,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    //用户验证
    private Boolean checkOrderOwner(String openid,String orderNo){
        Order order = orderRepository.findByOrderNo(orderNo);
        if (order==null){
            return false;
        }
        if (!order.getUserOpenid().equalsIgnoreCase(openid)){
            return false;
        }
        return true;
    }

}
