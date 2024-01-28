package com.xxx.jingdong.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Author；Aaron
 */
@Data
public class ProductCategoryBo {
    private Integer id;
    @JsonProperty("shop_id")
    private Integer shopId;
    private String name;
    private String type;
    private List<ProductBo> productBoList;
}
