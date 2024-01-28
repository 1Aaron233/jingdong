package com.xxx.jingdong.repository;

import com.xxx.jingdong.pojo.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author；Aaron
 */
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
    //根据订单查询
    List<OrderDetail> findByOrderNo(String orderNo);

}
