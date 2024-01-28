package com.xxx.jingdong.utils;

import com.xxx.jingdong.domain.Result;
import com.xxx.jingdong.enums.ResultEnum;

/**
 * Author；Aaron
 */
//统一格式工具类，一般应用于控制层做结果返回，与拦截类相关联
public class ResultUtil {

    //返回值为Result类型，此类为自定义泛型类，此方法被控制层调用时需写明特定的泛型
    //有结果成功
    public static Result success(Object object){
        Result result=new Result();
        result.setCode(200);
        result.setMsg("ok");
        result.setData(object);
        return result;
    }

    //无结果成功
    public static Result success(){

        return success(null);
    }

    //失败信息
    public static Result error(Integer code,String msg){
        Result result=new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    //失败信息，简化版
    public static Result error(ResultEnum resultEnum){
        Result result=new Result();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMessage());
        return result;
    }

}
