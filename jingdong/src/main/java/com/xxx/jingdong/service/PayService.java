package com.xxx.jingdong.service;

import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.xxx.jingdong.bo.OrderDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * Author；Aaron
 */
public interface PayService {
    /**
     * 统一下单
     * @param orderDTO
     * @param request
     * @return WxPayMpOrderResult
     */
    WxPayMpOrderResult create(OrderDTO orderDTO, HttpServletRequest request);

    /**
     *
     * @param request
     * @return String
     */
    String notify(HttpServletRequest request);

    /**
     *
     * @param orderDTO
     * @return WxPayRefundResult
     */
    WxPayRefundResult refund(OrderDTO orderDTO);
}
