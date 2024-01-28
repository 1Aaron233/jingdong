package com.xxx.jingdong.controller;

import com.xxx.jingdong.config.WechatAccountConfig;
import com.xxx.jingdong.enums.ResultEnum;
import com.xxx.jingdong.exception.SellException;
import com.xxx.jingdong.pojo.User;
import com.xxx.jingdong.service.UserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Author；Aaron
 * 微信SDK网页授权+扫码登录
 */
@Controller
@Slf4j
@RequestMapping("/wechat")
public class WechatController {
    //总结：1，导入依赖 2，写入配置信息 3，创建配置类 4，加入bean容器 5，编写SDK
    @Autowired
    private WxMpService wxMpService;
    @Qualifier("getWxOpenService")
    @Autowired
    private WxMpService wxOpenService;
    @Autowired
    private WechatAccountConfig wechatAccountConfig;
    @Autowired
    private UserService userService;

    /**
     * 微信公共平台
     * 一，引导用户打开同意授权页面
     * @param returnUrl
     * @return String
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/authorize")
    public String auth(@RequestParam("returnUrl") String returnUrl) throws UnsupportedEncodingException {
        //1，回调路径
        String url= wechatAccountConfig.getMpUserInfoUrl();
        //2,构造授权url
        //参数：redirectUri，scope，state
        String redirectUrl=wxMpService.getOAuth2Service().buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl,"utf-8"));
        log.info("[微信网页授权]获取code，result={}",redirectUrl);
        return "redirect:" + redirectUrl;
    }

    /**
     * 二，根据code获取AccessToken值以及根据AccessToken值获取用户信息并跳转
     * @param code
     * @param returnUrl
     * @return String
     */
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl){
        WxOAuth2AccessToken wxOAuth2AccessToken ;
        try {
            //1，AccessToken值获取
            wxOAuth2AccessToken=wxMpService.getOAuth2Service().getAccessToken(code);
        } catch (Exception e) {
            log.error("【微信网页授权】异常",e.getMessage());
            throw new SellException(ResultEnum.WECHET_MP_ERROR);
        }
        //2，获取用户信息，包括openid,nickname和头像等等
        WxOAuth2UserInfo wxMpUser = null;
        try {
            wxMpUser = wxMpService.getOAuth2Service().getUserInfo(wxOAuth2AccessToken,null);
        } catch (WxErrorException e) {
            e.getStackTrace();
        }
        log.info("【微信网页授权】resultUrl={},openId={}",returnUrl,wxMpUser);
        //3，执行数据库的对象入库等操作 TODO
        User user=userService.findByOpenid(wxMpUser.getOpenid());
        System.out.println(user);
        if (user.equals(null) ){
            user.setOpenid(wxMpUser.getOpenid());
            user.setUsername(wxMpUser.getNickname());
            user.setPassword("123456");
            userService.save(user);
        }
        //4，将带着获取到的信息跳转即可
        return "redirect:"+returnUrl+"?openId="+wxMpUser.getOpenid();
    }

    /**
     * 微信开放平台
     * 微信扫码登录
     * @return
     */
    @GetMapping("/qrAuthorize")
    public String qrAuthorize(){
        String url = wechatAccountConfig.getOpenUserInfoUrl();
        String redirectUrl =wxOpenService.buildQrConnectUrl(url,WxConsts.QrConnectScope.SNSAPI_LOGIN,null);
        log.info("【微信网页授权】redirectUrl={}",redirectUrl);
        return "redirect:"+redirectUrl;
    }

    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code){
        WxOAuth2AccessToken wxOAuth2AccessToken ;
        try {
            wxOAuth2AccessToken = wxOpenService.getOAuth2Service().getAccessToken(code);
        } catch (Exception e) {
            log.error("[微信网页授权]异常",e.getMessage());
            throw new SellException(ResultEnum.WECHET_MP_ERROR);
        }
        log.info("[WxOAuth2AccessToken]={}",wxOAuth2AccessToken);
        String openId =wxOAuth2AccessToken.getOpenId();
        log.info("[微信网页授权] openId={}",openId);
        return "redirect:/seller/scan"+"?openid="+openId;
    }

}
