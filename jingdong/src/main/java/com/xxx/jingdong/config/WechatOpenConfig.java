package com.xxx.jingdong.config;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Author；Aaron
 */
@Component
public class WechatOpenConfig {
    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    //@Bean注解类似于static的用法，表示在程序刚运行时即自动加载，等待调用
    @Bean
    public WxMpService getWxOpenService(){
        WxMpService wxMpService=new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxOpenConfigStorage());
        return wxMpService;
    }

    private WxMpConfigStorage wxOpenConfigStorage(){
        WxMpDefaultConfigImpl wxMpConfig=new WxMpDefaultConfigImpl();
        wxMpConfig.setAppId(wechatAccountConfig.getOpenAppId());
        wxMpConfig.setSecret(wechatAccountConfig.getOpenAppSecret());
        return wxMpConfig;
    }
}
