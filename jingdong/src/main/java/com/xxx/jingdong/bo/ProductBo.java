package com.xxx.jingdong.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

/**
 * Author；Aaron
 */
@Data
public class ProductBo {
    private Integer id;
    private String name;
    private Integer sales;
    private BigDecimal price;
    @JsonProperty("old_price")
    private BigDecimal oldPrice;
    //返回的json字段类型
    @JsonProperty("img_url")
    private String ImgUrl;
}
