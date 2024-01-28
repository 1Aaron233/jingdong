package com.xxx.jingdong.pojo;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Author；Aaron
 */
@Data
@Entity
@Table(name="hanma_seller")
@Proxy(lazy=false)
public class SellerInfo {
    @Id
    private String sellerId;
    private String username;
    private String password;
    private String openid;
}
