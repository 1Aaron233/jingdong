package com.xxx.jingdong.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Author；Aaron
 */
//微信网页授权配置类SDK
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {
    //微信公众号
    private String mpAppId;
    private String mpAppSecret;
    private String mpUserInfoUrl;
    //微信支付
    private String mchId;
    private String mchKey;
    private String keyPath;
    private String notifyUrl;
    //开放平台
    private String openAppId;
    private String openAppSecret;
    private String openUserInfoUrl;
    //一个radis的根路径，登录后保存登录状态用的
    private String baseUrl;
}
