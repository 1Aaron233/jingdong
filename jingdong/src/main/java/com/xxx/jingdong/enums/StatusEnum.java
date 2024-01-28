package com.xxx.jingdong.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * Author；Aaron
 */
@Getter

public enum StatusEnum implements CodeEnum{
    STATUS_NO(0,"审核"),
    STATUS_YES(1,"正常"),
    ;

    Integer code;
    String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
