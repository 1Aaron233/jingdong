package com.xxx.jingdong.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xxx.jingdong.enums.StateEnum;
import com.xxx.jingdong.enums.StatusEnum;
import com.xxx.jingdong.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Author；Aaron
 */
@Entity
@Table(name= "hanma_shop")
@TableName(value = "hanma_shop")
@Data
@Proxy(lazy = false)
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name= "cate_id")
    private Integer cateId;
    @TableField("`name`")
    private String name;
    private Integer sales;
    @Column(name= "express_limit")
    private Integer expressLimit;
    @Column(name= "express_price")
    private BigDecimal expressPrice;
    @TableField("`desc`")
    private String desc;
    @Column(name= "img_url")
    private String imgUrl;
    @Column(name= "seller_id")
    private String sellerId;
    private Integer state = StateEnum.STATE_NO.getCode();
    @TableField("`status`")
    private Integer status = StatusEnum.STATUS_YES.getCode();

    @JsonIgnore
    public StateEnum getStateEnum(){
        return EnumUtil.getByCode(state, StateEnum.class);
    }
    @JsonIgnore
    public StatusEnum getStatusEnum(){
        return EnumUtil.getByCode(status, StatusEnum.class);
    }

}

