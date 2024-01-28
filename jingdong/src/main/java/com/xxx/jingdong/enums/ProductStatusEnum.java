package com.xxx.jingdong.enums;

import lombok.Getter;

/**
 * Author；Aaron
 */
@Getter

public enum ProductStatusEnum implements CodeEnum{
    STATUS_NO(0,"下架"),
    STATUS_YES(1,"上架"),
    ;

    Integer code;
    String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
