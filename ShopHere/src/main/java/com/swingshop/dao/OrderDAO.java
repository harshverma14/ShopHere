package com.swingshop.dao;

import com.swingshop.db.DB;
import com.swingshop.model.Cart;
import com.swingshop.model.CartItem;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public int createOrder(int userId, Cart cart) throws SQLException {
        String insertOrder = "INSERT INTO orders(user_id, total) VALUES(?, ?)";
        String insertItem  = "INSERT INTO order_items(order_id, product_id, quantity, price) VALUES(?,?,?,?)";
        try (Connection c = DB.getConnection()) {
            try {
                c.setAutoCommit(false);
                BigDecimal total = cart.total();
                int orderId;
                try (PreparedStatement ps = c.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, userId); ps.setBigDecimal(2, total);
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) { keys.next(); orderId = keys.getInt(1); }
                }
                try (PreparedStatement psi = c.prepareStatement(insertItem)) {
                    for (CartItem ci : cart.all()) {
                        psi.setInt(1, orderId);
                        psi.setInt(2, ci.product.id);
                        psi.setInt(3, ci.quantity);
                        psi.setBigDecimal(4, ci.product.price);
                        psi.addBatch();
                    }
                    psi.executeBatch();
                }
                String dec = "UPDATE products SET stock = stock - ? WHERE id=? AND stock >= ?";
                try (PreparedStatement psd = c.prepareStatement(dec)) {
                    for (CartItem ci : cart.all()) {
                        psd.setInt(1, ci.quantity);
                        psd.setInt(2, ci.product.id);
                        psd.setInt(3, ci.quantity);
                        int upd = psd.executeUpdate();
                        if (upd == 0) throw new SQLException("Insufficient stock for product " + ci.product.id);
                    }
                }
                c.commit();
                return orderId;
            } catch (SQLException ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }

    public List<String> listOrdersForUser(int userId) throws SQLException {
        String sql = "SELECT id, total, created_at FROM orders WHERE user_id=? ORDER BY id DESC";
        try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                List<String> rows = new ArrayList<>();
                while (rs.next()) {
                    rows.add("#" + rs.getInt(1) + "  ₹" + rs.getBigDecimal(2) + "  @ " + rs.getTimestamp(3));
                }
                return rows;
            }
        }
    }
}
