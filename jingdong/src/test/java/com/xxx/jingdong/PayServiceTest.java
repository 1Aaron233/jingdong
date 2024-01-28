package com.xxx.jingdong;

import com.xxx.jingdong.bo.OrderDTO;
import com.xxx.jingdong.service.OrderService;
import com.xxx.jingdong.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Author；Aaron
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayService payService;
    @Test
    public void orderTest(){
        MockHttpServletRequest request=new MockHttpServletRequest();
        OrderDTO orderDTO=orderService.findOne("1703407541987284856");
        payService.create(orderDTO,request);
    }
}
