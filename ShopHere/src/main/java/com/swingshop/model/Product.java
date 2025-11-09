package com.swingshop.model;

import java.math.BigDecimal;

public class Product {
    public int id;
    public String name;
    public BigDecimal price;
    public int stock;

    public Product(int id, String name, BigDecimal price, int stock) {
        this.id = id; this.name = name; this.price = price; this.stock = stock;
    }
}
