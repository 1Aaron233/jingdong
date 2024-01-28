package com.xxx.jingdong.enums;

import lombok.Getter;

/**
 * Author；Aaron
 */
@Getter
public enum StateEnum implements CodeEnum{
    STATE_NO(0,"正常"),
    STATE_YES(1,"推荐"),
    ;

    Integer code;
    String message;

    StateEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
