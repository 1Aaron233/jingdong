package com.xxx.jingdong.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * Author；Aaron
 * 微信原生授权类
 */
@Controller
@RequestMapping("weixin")
@Slf4j
public class WeixinController {
    @Autowired
    private  RestTemplate restTemplate;
    //一，授权后重定向的回调链接地址，我们自己应用的地址，用来接收code并且重新向微信发出请求根据code获取access_token
    //参数appid   redirect_uri  response_type scope state
    private  String weixinUrl="https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s" +
                              "&response_type=code&scope=%s&state=%s#wechat_redirect";
    //二，根据拿到的code，获取access_token以及openid
    private  String tokenUrl= "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s" +
                              "&code=%s&grant_type=authorization_code";
    //三，根据access_token以及openid,获取用户信息
    private  String userUrl= "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
    //上述所需参数
    private  String appid="wxe16e212405cdab57";
    private String appSecret ="8145e229eee3344c423ac6bae7b1855c";
    // （ 弹出授权页面，可通过openid拿到昵称、性别、所在地。并且,即使在未关注的情况下，只要用户授权，也能获取其信息 ）
    private  String scope="snsapi_userinfo";
    //授权后重定向的回调链接地址，我们自己应用的地址，用来接收code并且重新向微信发出请求根据code获取access_token
    private  String redirectUri="http://a7vjdg.natappfree.cc/jingdong/weixin/userInfo";

    //一，跳转到微信传送code并获取access_token然后根据传过去uri使微信返回code
    @GetMapping("auth")
    public String auth(String redirect_uri){
         String url=String.format(weixinUrl,appid,redirectUri,scope,redirect_uri);
         //重定向
         return "redirect:"+url;
    }

    @GetMapping("userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String redirect_uri){
        log.info("code={}",code);
        //二,使用get方式获取accessToken以及openId
        String url=String.format(tokenUrl,appid,appSecret,code);
        String result = restTemplate.getForObject(url,String.class);
        log.info("json字符串版第二步={}",result);
        //把json格式字符串解析为java对象，目的是获取特定值
        JSONObject jsonObject= JSON.parseObject(result);
        log.info("对象版第二步={}",jsonObject);
        //三,getUserInfo 拉取用户信息
        jsonObject=getUserInfo(jsonObject.getString("access_token"),jsonObject.getString("openid"));
        log.info("对象版第二步={}",jsonObject);
        return "redirect:"+redirect_uri;
    }

    private JSONObject getUserInfo(String accessToken,String openid) {
        String url = String.format(userUrl, accessToken, openid);
        String result = restTemplate.getForObject(url, String.class);
        //把json格式字符串解析为java对象，目的是获取特定值
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject;
    }
}
