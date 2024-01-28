package com.xxx.jingdong.enums;

import lombok.Getter;

/**
 * Author；Aaron
 */
@Getter
public enum ResultEnum {
    //店铺相关10~20
    SHOPCATEORY_NOT_EXIST(10,"店铺分类不存在"),
    SHOP_NOT_EXIST(11,"店铺不存在"),
    //商品相关20~30
    PRODUCTCATEGORY_NOT_EXIST(20,"商品分类不存在"),
    PRODUCT_NOT_EXIST(21,"商品不存在"),
    PRODUCT_STOCK_ERROR(22,"更新库存错误"),
    //密码登录30~40
    USER_PARAMS_ERROR(31,"用户参数错误"),
    USER_PASSWORD_DISCORDANT(32,"密码不一致"),
    USER_EXISIT_YES(33,"该用户已存在"),
    USER_REGISTER_ERROR(34,"用户注册失败"),
    USER_REQUEST_ERROR(35,"用户请求不合法"),
    USER_PASSWORD_ERROR(36,"用户密码不正确"),
    USER_EXISIT_NO(37,"该用户不存在"),
    //微信SDK授权40~50
    WECHET_MP_ERROR(40,"微信公众号方面错误"),
    //地址50~60
    ADDRESS_EXISIT(50,"地址不存在"),
    ADDRESS_PARAMS_ERROR(51,"地址参数错误"),
    ADDRESS_ADD_ERROR(52,"地址添加错误"),
    ADDRESS_UPDATE_ERROR(53,"地址更新失败"),
    ADDRESS_USER_ERROR(54,"非法操作"),
    //订单60~70
    ORDER_PARAMS_ERROR(60,"订单参数错误"),
    ORDER_NOT_EXIST(61,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(62,"订单详情不存在"),
    ORDER_STATUS_ERROR(63,"订单状态不正确"),
    ORDER_UPDATE_FAIL(64,"订单更新失败"),
    ORDER_DETAIL_EMPTY(65,"订单详情为空"),
    ORDER_PAY_STATUS_ERROR(66,"订单支付状态不正确"),
    ORDER_CART_EMPTY(67,"订单购物车为空"),
    ORDER_OWNER_ERROR(68,"订单不属于当前用户"),
    ORDER_CANCEL_SUCCESS(69,"订单取消成功"),
    ORDER_FINISH_SUCCESS(70,"订单已完成"),
    //微信支付71~80
    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(71,"微信支付异步通知金额效验不通过"),
    WXPAY_NOTIFY_OPENID_VERIFY_ERROR(72,"微信支付异步通知openid效验不通过"),
    WXPAY_REFUND_ERROR(73,"微信支付退款失败"),
    //第三方微信登录 81~90
    LOGIN_FAIL(81,"登录失败，登录信息不准确"),
    LOGOUT_SUCCESS(82,"登出成功"),
    //卖家端商品
    PRODUCT_ADD_SUCCESS(91,"商品添加成功"),
    PRODUCT_UPDATE_SUCCESS(92,"商品更新成功"),
    PRODUCT_DELETE_SUCCESS(93,"商品删除成功"),
    PRODUCT_UP_SUCCESS(94,"商品上架成功"),
    PRODUCT_DOWN_SUCCESS(95,"商品下架成功"),
    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
