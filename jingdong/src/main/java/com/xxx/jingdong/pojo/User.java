package com.xxx.jingdong.pojo;

import com.xxx.jingdong.enums.StatusEnum;
import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Author；Aaron
 */
@Data
@Entity
@Table(name="hanma_user")
@Proxy(lazy=false)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String openid;
    private String password;
    private String numbers;
    private String nickname;
    private String mobile;
    private BigDecimal money;
    private Integer coupons;
    private BigDecimal gold;
    private BigDecimal iou;
    private Integer status = StatusEnum.STATUS_YES.getCode();
}
