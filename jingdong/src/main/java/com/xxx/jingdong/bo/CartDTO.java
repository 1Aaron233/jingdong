package com.xxx.jingdong.bo;

import lombok.Data;

/**
 * Author；Aaron
 */
//库存
@Data
public class CartDTO {
    /*商品id*/
    private Integer productId;
    /*商品数量*/
    private Integer productQuantity;

    public CartDTO(Integer productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
