package com.xxx.jingdong.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

/**
 * Author；Aaron
 */
@Data
public class OrderForm {
    //买家userid
    @Min(message = "userid必填",value = 1)
    private Integer userId;
    //买家姓名
    @NotBlank(message = "姓名必填")
    private String name;
    //买家手机号
    @NotBlank(message = "手机号必填")
    private String phone;
    //买家地址
    @NotBlank(message = "地址必填")
    private String address;
    //买家openid
    @NotBlank(message = "openid必填")
    private String openid;
    //商铺id
    @Min(message = "商铺id必填",value=1)
    private Integer shopId;
    //商铺姓名
    @NotBlank(message = "商铺名必填")
    private String shopName;
    //购物车
    @NotBlank(message = "购物车不能为空")
    private String products;
}
