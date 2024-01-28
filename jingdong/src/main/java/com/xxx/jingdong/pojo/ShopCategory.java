package com.xxx.jingdong.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xxx.jingdong.enums.PayStatusEnum;
import com.xxx.jingdong.enums.StatusEnum;
import com.xxx.jingdong.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * Author；Aaron
 */
@Entity
@Table(name= "hanma_shop_category")
@Data
@Proxy(lazy = false)
public class ShopCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "img_url")
    private String imgUrl;
    private Integer status = StatusEnum.STATUS_YES.getCode();

    @JsonIgnore
    public StatusEnum getStatusEnum(){
        return EnumUtil.getByCode(status, StatusEnum.class);
    }
}

