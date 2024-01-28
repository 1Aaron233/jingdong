package com.xxx.jingdong.pojo;

import com.xxx.jingdong.enums.DefaultEnum;
import lombok.Data;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Author；Aaron
 */
@Entity
@Table(name= "hanma_address")
@Data
@Proxy(lazy = false)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    @NotEmpty(message="姓名不能为空")
    private String real_name;
    @NotEmpty(message="手机不能为空")
    private String mobile;
    @NotEmpty(message="省市不能为空")
    private String city;
    @NotEmpty(message="区域不能为空")
    private String area;
    private String house;
    private Integer isDefault= DefaultEnum.DEFAULT_NO.getCode();
}
