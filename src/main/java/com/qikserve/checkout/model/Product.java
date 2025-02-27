package com.qikserve.checkout.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Product {
    private String id;
    private String name;
    private int price; 
    private List<Promotion> promotions;

    public Product() {}

    public Product(String id, String name, int price, List<Promotion> promotions) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.promotions = promotions;
    }
}
