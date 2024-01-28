package com.xxx.jingdong.exception.handler;

import com.xxx.jingdong.domain.Result;
import com.xxx.jingdong.exception.SellException;
import com.xxx.jingdong.utils.ResultUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author；Aaron
 */
@ControllerAdvice
public class HandlerException {
    //添加与特定错误绑定的注解，属于全局配置
    @ExceptionHandler(value = SellException.class)
    @ResponseBody//要的是json格式
    //此处参数中对象信息由控制层错误类处拦截获得，由此参数调用统一格式工具类已返回对应数据。
    public Result handler(SellException e){
        return ResultUtil.error(e.getCode(),e.getMessage());
    }
}
