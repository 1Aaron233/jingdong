package com.xxx.jingdong.controller;

import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.xxx.jingdong.bo.OrderDTO;
import com.xxx.jingdong.domain.Result;
import com.xxx.jingdong.enums.OrderStatusEnum;
import com.xxx.jingdong.enums.PayStatusEnum;
import com.xxx.jingdong.enums.ResultEnum;
import com.xxx.jingdong.exception.SellException;
import com.xxx.jingdong.service.OrderService;
import com.xxx.jingdong.service.PayService;
import com.xxx.jingdong.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 * @Author:Aaron
 * @Create:2023/8/1 - 18:50
 * @Version:v1.0
 *
 */
@RequestMapping("pay")
@Controller
@Slf4j
public class PayController {
    @Autowired
    PayService payService;
    @Autowired
    OrderService orderService;

    /**
     * 微信支付统一下单
     * @param orderNo
     * @param returnUrl
     * @param map
     * @param request
     * @return ModelAndView
     */
    @GetMapping("/create")
    public ModelAndView payController(@RequestParam("orderNo") String orderNo,
                                      @RequestParam("returnUrl") String returnUrl,
                                      Map<String,Object> map,
                                      HttpServletRequest request){
    //1,查询订单
    OrderDTO orderDTO = orderService.findOne(orderNo);
    if (orderDTO == null){
        throw new SellException(ResultEnum.ORDER_NOT_EXIST);
    }
    //2,统一下单
    /*请求下单接口，创建订单
    * 请求需要参数：appid，商户号，随机字符串，签名，订单号，IP地址。。。。。
    * 返回参数：appid，商户号，随机字符串，签名，预支付交易会话标识（预订单id）
    * */
    WxPayMpOrderResult payResponse = payService.create(orderDTO,request);

    map.put("payResponse",payResponse);
    ModelAndView mv = new ModelAndView();
    mv.addAllObjects(map);
    mv.addObject("returnUrl",returnUrl);
    /*跳转H5页面发起JSAPI支付
    * 需要参数：appid，时间戳，随机字符串，签名，预支付ID
    *  */
    mv.setViewName("/pay/create");
    System.out.print (mv);
    return mv;
    }

    /**
     * 微信支付异步通知
     * @param request
     * @return String
     */
    @PostMapping("/notify")
    @ResponseBody
    public String notify(HttpServletRequest request){
        /*异步回调修改数据库,改变支付状态
         *微信请求本地，携带参数：appid，订单号，订单金额。。。与数据库对比，---->返回微信通知  不成功给用户退款
        * */
        log.info("【微信回调成功】");
        String payResponse = payService.notify(request);
        //返回的是个字符串
        return payResponse;
    }

    /**
     * 微信退款
     * @param orderNo
     * @return Result<WxPayRefundResult>
     */
    @PostMapping("/refund")
    @ResponseBody
    public Result<WxPayRefundResult> refund(@RequestParam("orderNo") String orderNo){
        //1,查询订单
        OrderDTO orderDTO = orderService.findOne(orderNo);
        if (orderDTO == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //支付成功并且未发货，进入退款函数
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())
           && orderDTO.getOrderStatus().equals(OrderStatusEnum.SEND_NO.getCode())){
            return ResultUtil.success(payService.refund(orderDTO));
        }
        //退款失败
        return ResultUtil.error(ResultEnum.WXPAY_REFUND_ERROR);
    }

}
