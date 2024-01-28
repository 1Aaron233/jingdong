package com.xxx.jingdong;


import com.xxx.jingdong.bo.OrderDTO;
import com.xxx.jingdong.pojo.OrderDetail;
import com.xxx.jingdong.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Author；Aaron
 */
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
//生成订单测试
public class OrderServiceTest {
    private final String BUY_OPENID="12345678919";
    private final String BUY_ORDERID="1703400387762269197";
    @Autowired
    private OrderService orderService;
    /*生成订单*/
    @Test
    public void createTest(){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(1);
        orderDTO.setUserName("汉码未来");
        orderDTO.setUserAddress("济南");
        orderDTO.setUserPhone("18812345678");
        orderDTO.setUserOpenid(BUY_OPENID);
        orderDTO.setShopId(1);
        orderDTO.setShopName("汉码店铺");
        //购物车
        List<OrderDetail>  orderDetailList=new ArrayList<>();
        OrderDetail o1=new OrderDetail();
        o1.setProductId(1);
        o1.setQuantity(1);
        orderDetailList.add(o1);
        OrderDetail o2=new OrderDetail();
        o2.setProductId(2);
        o2.setQuantity(2);
        orderDetailList.add(o2);
        orderDTO.setOrderDetailList(orderDetailList);

        String sellerId="1";
        OrderDTO result=orderService.create(orderDTO);
        log.info("【创建订单】result={}",result);
    }

    @Test
    public void findOneTest(){
        OrderDTO result=orderService.findOne(BUY_ORDERID);
        log.info("【查询单个订单】result={}",result);
    }

    @Test
    public void findListTest(){
        PageRequest request =PageRequest.of(0,2, Sort.by(Sort.Direction.DESC,"orderNo"));
        Page<OrderDTO> orderDTOs=orderService.findList(BUY_OPENID,request);
        log.info("[查询多个订单]result={}",orderDTOs.getContent());
    }
//
//    @Test
//    public void cancel(){
//        OrderDTO result=orderService.cancel(BUY_ORDERID);
//        log.info("[取消订单]result={}",result);
//    }

    @Test
    public void finishTest(){
        OrderDTO result=orderService.finish(BUY_ORDERID);
        log.info("[支付状态]result={}",result);
    }

    @Test
    public void paidTest(){
        OrderDTO result=orderService.paid(BUY_ORDERID);
        log.info("[支付状态]result={}",result);
    }
}
