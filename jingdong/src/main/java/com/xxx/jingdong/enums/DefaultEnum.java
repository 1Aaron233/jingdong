package com.xxx.jingdong.enums;

import lombok.Getter;

/**
 * Author；Aaron
 */
//与默认地址相关联
@Getter
public enum DefaultEnum {

    DEFAULT_NO(0,"普通"),
    DEFAULT_YES(1,"默认"),
    ;

    Integer code;
    String message;

    DefaultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
