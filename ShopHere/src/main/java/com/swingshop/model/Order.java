package com.swingshop.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {
    public int id;
    public int userId;
    public BigDecimal total;
    public LocalDateTime createdAt;
}
