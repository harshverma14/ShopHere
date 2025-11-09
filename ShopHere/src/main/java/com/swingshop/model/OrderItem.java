package com.swingshop.model;

import java.math.BigDecimal;

public class OrderItem {
    public int productId;
    public int quantity;
    public BigDecimal price;

    public OrderItem(int productId, int quantity, BigDecimal price) {
        this.productId = productId; this.quantity = quantity; this.price = price;
    }
}
