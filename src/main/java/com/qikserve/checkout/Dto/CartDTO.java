package com.qikserve.checkout.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartDTO {
    private List<CartItemDTO> items;

    public CartDTO() {}

    public CartDTO(List<CartItemDTO> items) {
        this.items = items;
    }
}
