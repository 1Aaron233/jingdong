package com.xxx.jingdong.domain;

import lombok.Data;

/**
 * Author；Aaron
 */
//统一格式实体类，与统一格式工具类相关联
@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

}
