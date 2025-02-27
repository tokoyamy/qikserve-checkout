package com.qikserve.checkout.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {
    private String productId;
    private int quantity;

    public CartItemDTO() {}

    public CartItemDTO(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
