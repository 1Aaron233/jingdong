package com.xxx.jingdong.enums;

import lombok.Getter;

/**
 * Author；Aaron
 */
@Getter
public enum OrderStatusEnum implements CodeEnum{
    NEW(0,"未支付"),
    FINISHED(1,"已完结"),
    CANCEL(2,"已取消"),
    SEND_NO(3,"未发货"),
    SEND_YES(4,"已发货"),
    ;

    Integer code;
    String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
