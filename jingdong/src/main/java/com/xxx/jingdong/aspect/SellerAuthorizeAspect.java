package com.xxx.jingdong.aspect;

import com.xxx.jingdong.constant.CookieConstant;
import com.xxx.jingdong.constant.RedisConstant;
import com.xxx.jingdong.exception.SellerAuthorException;
import com.xxx.jingdong.utils.CookieUtil;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Author；Aaron
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.xxx.jingdong.controller.Sell*.*(..))"+
              "&&!execution(public * com.xxx.jingdong.controller.SellerUserController.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request= attributes.getRequest();
        //查询cookie
        Cookie cookie= CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null){
            log.warn("[登录效验]cookie中查不到token");
            //被拦截后转到拼接的链接处
            throw new SellerAuthorException();
        }
        //去redis里面查询
        String tokenValue=redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        if (StringUtil.isNullOrEmpty(tokenValue)){
            log.warn("[登录效验]redis中查不到token");
            throw new SellerAuthorException();
        }
    }
}
