package com.qikserve.checkout.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Promotion {
    private String id;
    private String type;
    private int requiredQty;
    private int freeQty;
    private int amount;
    private int price;

    public Promotion() {}

    public Promotion(String id, String type, int requiredQty, int freeQty, int amount, int price) {
        this.id = id;
        this.type = type;
        this.requiredQty = requiredQty;
        this.freeQty = freeQty;
        this.amount = amount;
        this.price = price;
    }
}
