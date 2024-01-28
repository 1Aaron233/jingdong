package com.xxx.jingdong.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xxx.jingdong.enums.StatusEnum;
import com.xxx.jingdong.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * Author；Aaron
 */
@Entity
@Table(name= "hanma_product_category")
@Data
@Proxy(lazy = false)
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "shop_id")
    private Integer shopId;
    private String name;
    private String type;
    private Integer status = StatusEnum.STATUS_YES.getCode();

    @JsonIgnore
    public StatusEnum getStatusEnum(){
        return EnumUtil.getByCode(status, StatusEnum.class);
    }
}

