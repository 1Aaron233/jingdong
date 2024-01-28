package com.xxx.jingdong.repository;

import com.xxx.jingdong.pojo.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Author；Aaron
 */
@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
    //根据订单号查询
    Order findByOrderNo(String orderNo);
    //根据openid查询分页
    Page<Order> findByUserOpenid(String openid, Pageable pageable);
    //根据SellerId查询分页
    Page<Order> findBySellerId(Pageable pageable,String sellerId);
}
