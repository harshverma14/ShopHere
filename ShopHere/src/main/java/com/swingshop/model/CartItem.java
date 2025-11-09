package com.swingshop.model;

import java.math.BigDecimal;

public class CartItem {
    public Product product;
    public int quantity;
    public CartItem(Product p, int q) { this.product = p; this.quantity = q; }
    public BigDecimal lineTotal() { return product.price.multiply(new BigDecimal(quantity)); }
}
