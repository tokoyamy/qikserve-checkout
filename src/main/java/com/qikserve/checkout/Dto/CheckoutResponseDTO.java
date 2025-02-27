package com.qikserve.checkout.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutResponseDTO {
    private int total;
    private int savings;

    public CheckoutResponseDTO() {}

    public CheckoutResponseDTO(int total, int savings) {
        this.total = total;
        this.savings = savings;
    }
}
