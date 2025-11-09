package com.swingshop.model;

import java.math.BigDecimal;
import java.util.*;

public class Cart {
    private final Map<Integer, CartItem> items = new LinkedHashMap<>();

    public void add(Product p, int qty) {
        CartItem existing = items.get(p.id);
        if (existing == null) items.put(p.id, new CartItem(p, qty));
        else existing.quantity += qty;
    }

    public void setQty(int productId, int qty) {
        CartItem ci = items.get(productId);
        if (ci != null) ci.quantity = qty;
    }

    public void remove(int productId) { items.remove(productId); }
    public void clear() { items.clear(); }
    public Collection<CartItem> all() { return items.values(); }

    public BigDecimal total() {
        return items.values().stream()
                .map(CartItem::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isEmpty() { return items.isEmpty(); }
}
