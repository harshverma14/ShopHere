package com.swingshop.dao;

import com.swingshop.db.DB;
import com.swingshop.model.User;
import com.swingshop.util.HashUtil;

import java.sql.*;

public class UserDAO {
    public User findByUsernameAndPassword(String username, String password) throws SQLException {
        String sql = "SELECT id, username FROM users WHERE username=? AND password_hash=?";
        try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, HashUtil.sha256(password));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new User(rs.getInt(1), rs.getString(2));
                return null;
            }
        }
    }

    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE username=?";
        try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    public User register(String username, String password) throws SQLException {
        String sql = "INSERT INTO users(username, password_hash) VALUES(?, ?)";
        try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ps.setString(2, HashUtil.sha256(password));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return new User(keys.getInt(1), username);
                throw new SQLException("No key returned");
            }
        }
    }

    public void updatePassword(int userId, String newPassword) throws SQLException {
        String sql = "UPDATE users SET password_hash=? WHERE id=?";
        try (Connection c = DB.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, HashUtil.sha256(newPassword));
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }
}
