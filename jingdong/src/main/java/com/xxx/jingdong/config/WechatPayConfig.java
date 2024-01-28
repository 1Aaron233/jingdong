package com.xxx.jingdong.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Author；Aaron
 */
@Component
public class WechatPayConfig {
    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Bean
    public WxPayService wxPayService(){
         WxPayService wxPayService=new WxPayServiceImpl();
         wxPayService.setConfig(wxPayConfig());
         return wxPayService;
    }

    private WxPayConfig wxPayConfig(){
        WxPayConfig payConfig=new WxPayConfig();
        payConfig.setAppId(wechatAccountConfig.getMpAppId());
        payConfig.setMchId(wechatAccountConfig.getMchId());
        payConfig.setMchKey(wechatAccountConfig.getMchKey());
        payConfig.setKeyPath(wechatAccountConfig.getKeyPath());
        payConfig.setNotifyUrl(wechatAccountConfig.getNotifyUrl());
        return payConfig;
    }
}
