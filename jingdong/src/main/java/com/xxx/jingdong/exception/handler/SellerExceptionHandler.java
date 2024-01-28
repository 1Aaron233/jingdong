package com.xxx.jingdong.exception.handler;

import com.xxx.jingdong.config.WechatAccountConfig;
import com.xxx.jingdong.exception.SellerAuthorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Author；Aaron
 */
@ControllerAdvice
public class SellerExceptionHandler {
    @Autowired
    private WechatAccountConfig accountConfig;

    @ExceptionHandler(value = SellerAuthorException.class)
    public ModelAndView handlerAuthorizeException(){
        //返回模板类型数据
        return new ModelAndView("redirect:"
        .concat(accountConfig.getBaseUrl())
        .concat("/jingdong/seller/login"));
    }
}
