package com.swingshop.dao;

import com.swingshop.db.DB;
import com.swingshop.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public List<Product> listAll() throws SQLException {
        String sql = "SELECT id, name, price, stock FROM products ORDER BY id";
        try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Product> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1), rs.getString(2), rs.getBigDecimal(3), rs.getInt(4)));
            }
            return list;
        }
    }

    public Product byId(int id) throws SQLException {
        String sql = "SELECT id, name, price, stock FROM products WHERE id=?";
        try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Product(rs.getInt(1), rs.getString(2), rs.getBigDecimal(3), rs.getInt(4));
                return null;
            }
        }
    }
}
