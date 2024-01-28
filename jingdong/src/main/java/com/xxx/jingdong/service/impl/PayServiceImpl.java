package com.xxx.jingdong.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.service.WxPayService;
import com.xxx.jingdong.bo.OrderDTO;
import com.xxx.jingdong.enums.PayStatusEnum;
import com.xxx.jingdong.enums.ResultEnum;
import com.xxx.jingdong.exception.SellException;
import com.xxx.jingdong.service.OrderService;
import com.xxx.jingdong.service.PayService;
import com.xxx.jingdong.utils.IPUtils;
import com.xxx.jingdong.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Author；Aaron
 */
/*
此类为一个和微信的连结操作，包括支付订单的创建一个通知的返回，以及一个回调的操作
* **/
@Service
@Slf4j
public class PayServiceImpl implements PayService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private WxPayService wxPayService;

    @Override
    public WxPayMpOrderResult create(OrderDTO orderDTO, HttpServletRequest request) {
        WxPayMpOrderResult orderResult = null;
        try {
        WxPayUnifiedOrderRequest orderRequest=new WxPayUnifiedOrderRequest();
        orderRequest.setBody(orderDTO.getShopName());//主题
        orderRequest.setOutTradeNo(orderDTO.getOrderNo());//订单号
        orderRequest.setTotalFee(BaseWxPayRequest.yuanToFen(orderDTO.getOrderAmount().toString()));//总价,元转成分
        orderRequest.setOpenid(orderDTO.getUserOpenid());//openid
        orderRequest.setSpbillCreateIp(IPUtils.getIpAddr(request));//用户ip
        orderRequest.setTradeType(WxPayConstants.TradeType.JSAPI);//支付类型
        log.info("[微信支付]request={}", JSON.toJSON(orderRequest));
        //去请求,返回结果
        orderResult = wxPayService.createOrder(orderRequest);
        log.info("[微信支付]payResponse={}",JSON.toJSON(orderResult));
        } catch (Exception e) {
            log.info("微信支付失败！订单号：{}，原因：{}",orderDTO.getOrderNo(),e.getMessage());
        }
        return orderResult;
    }

    //信息返回
    @Override
    public String notify(HttpServletRequest request) {
        WxPayOrderNotifyResult payResponse;
        try {
            String xmlResult = IOUtils.toString(request.getInputStream(),request.getCharacterEncoding());
            payResponse = wxPayService.parseOrderNotifyResult(xmlResult);
            log.info("[微信支付]异步通知payResponse={}",JSON.toJSON(payResponse));
        } catch (Exception e) {
            log.info("微信支付回调结果异常，异常原因{}",e.getMessage());
            return WxPayNotifyResponse.fail(e.getMessage());
        }
        //加入自己处理订单的业务逻辑，需要判断订单是否已经支付过，否则可能会重复调用
        //1,验证签名
        //2.判断订单是否存在
        String orderNo = payResponse.getOutTradeNo();
        OrderDTO orderDTO = orderService.findOne(orderNo);
        if (orderDTO == null){
            log.error("[微信支付]异步通知,订单不存在 orderId={}",orderNo);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //3,支付的状态
        if(orderDTO.getPayStatus() != PayStatusEnum.WAIT.getCode()){
            log.info("[微信支付]异步通知，支付状态不正确 paystatus={}",orderDTO.getPayStatus());
            log.info("[微信支付]账号疑似已被支付");
            return WxPayNotifyResponse.success("支付成功");
        }
        //4,支付人
        if (!orderDTO.getUserOpenid().equals(payResponse.getOpenid())){
            log.info("[微信支付]异步通知，openid不一致，openid ={}",payResponse.getOpenid());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_OPENID_VERIFY_ERROR);
        }
        //判断金额是否一致
        String totalFee = BaseWxPayResult.fenToYuan(payResponse.getTotalFee());//转为元
        if (!MathUtil.equals(orderDTO.getOrderAmount().doubleValue(),Double.valueOf(totalFee))){
            log.info("[微信支付]异步通知，订单金额不一致，orderId ={},微信通知金额={},系统金额={}",payResponse.getOutTradeNo(),totalFee,orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }
        //这个是流水交易号，是需要保存到数据库中的
        String tradeNo = payResponse.getTransactionId();
        System.out.println(tradeNo);
        //修改订单支付状态
        orderService.paid(orderNo);
        log.info("[微信支付]异步通知，支付成功，payResponse={}",JSON.toJSON(WxPayNotifyResponse.success("支付成功")));
        return WxPayNotifyResponse.success("支付成功");
    }

    //微信退款
    @Override
    public WxPayRefundResult refund(OrderDTO orderDTO) {
        WxPayRefundResult refundResult =null;
        try {
            WxPayRefundRequest refundRequest = new WxPayRefundRequest();
            refundRequest.setOutTradeNo(orderDTO.getOrderNo());
            refundRequest.setOutRefundNo(orderDTO.getOrderNo());
            refundResult.setTotalFee(BaseWxPayRequest.yuanToFen(orderDTO.getOrderAmount().toString()));
            refundResult.setRefundFee(BaseWxPayRequest.yuanToFen(orderDTO.getOrderAmount().toString()));
            refundRequest.setOpUserId(orderDTO.getUserOpenid());
            log.info("[微信退款] refundRequest={}",JSON.toJSON(refundRequest));
            refundResult =wxPayService.refund(refundRequest);
            log.info("[微信退款] refundResult={}",JSON.toJSON(refundResult));
        } catch (Exception e) {
            log.error("微信退款失败！订单号：{}，原因{}",orderDTO.getOrderNo(),e.getMessage());
        }
        return refundResult;
    }

}
