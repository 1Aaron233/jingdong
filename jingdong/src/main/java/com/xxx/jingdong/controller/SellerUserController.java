package com.xxx.jingdong.controller;

import com.xxx.jingdong.config.WechatAccountConfig;
import com.xxx.jingdong.constant.CookieConstant;
import com.xxx.jingdong.constant.RedisConstant;
import com.xxx.jingdong.enums.ResultEnum;
import com.xxx.jingdong.pojo.SellerInfo;
import com.xxx.jingdong.service.SellerService;
import com.xxx.jingdong.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Author；Aaron
 */
@Controller
@RequestMapping("/seller")
@Slf4j
public class SellerUserController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private StringRedisTemplate redisTemplate;//爆红也没有关系
    @Autowired
    private WechatAccountConfig accountConfig;

    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("login/login");
        return mv;
    }

    //扫码登录接口
    @GetMapping("/scan")
    public ModelAndView scan(@RequestParam("openid") String openid, HttpServletResponse response, HttpSession session){
        ModelAndView mv=new ModelAndView();
        //1,openid去和数据库里的数据匹配
        SellerInfo sellerInfo=sellerService.findSellerInfoByOpenid(openid);
        System.out.println(sellerInfo);
        if (sellerInfo ==null){
            mv.addObject("msg", ResultEnum.LOGIN_FAIL.getMessage());
            mv.addObject("url","/jingdong/seller/login");
            mv.setViewName("common/error");
            return mv;
        }
        save(session,sellerInfo,response,openid);
        //不能直接setViewName，那会直接跳转到页面
        return new ModelAndView("redirect:"+accountConfig.getBaseUrl()+"/jingdong/seller/order/list?");
    }

    //账户密码登录接口
    @GetMapping("/password")
    public ModelAndView password(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 HttpServletResponse response,HttpSession session){
        ModelAndView mv=new ModelAndView();
        System.out.println(username);
        //1,openid去和数据库里的数据匹配
        SellerInfo sellerInfo=sellerService.findByUsernameAndPassword(username,password);
        System.out.println(sellerInfo);
        if (sellerInfo ==null){
            mv.addObject("msg", ResultEnum.LOGIN_FAIL.getMessage());
            mv.addObject("url","/jingdong/seller/login");
            mv.setViewName("common/error");
            return mv;
        }
        save(session,sellerInfo,response,sellerInfo.getOpenid());
        return new ModelAndView("redirect:"+accountConfig.getBaseUrl()+"/jingdong/seller/order/list?sellerId="+sellerInfo.getSellerId());
    }

    //登出接口
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response,HttpSession session){
        //1，从cookie里查询
        Cookie cookie =CookieUtil.get(request,CookieConstant.TOKEN);
        if (cookie != null){
            //2，清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            //3,清除cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        session.removeAttribute("sellerId");
        ModelAndView mv=new ModelAndView();
        mv.setViewName("login/login");
        return mv;
    }

    private void save(HttpSession session,SellerInfo sellerInfo,HttpServletResponse response,String openid){
        //2,把信息存到redis里
        String token = UUID.randomUUID().toString().replace("-","");
        Integer expire= RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire, TimeUnit.SECONDS);
        //3,把信息存到cookie里
        CookieUtil.set(response, CookieConstant.TOKEN,token,expire);
        //创建一个Session存sellerId供以后使用
        session.setAttribute("sellerId",sellerInfo.getSellerId());
    }
}
