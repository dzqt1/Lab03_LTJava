package com.solipsism.server.dao;

import java.sql.*;
import java.util.*;
import com.solipsism.server.util.DBConnection;
import com.solipsism.server.model.User;
import java.sql.Timestamp;

public class UserDAO {

    public UserDAO() {
    }

    public boolean saveUser(User user) {
        String sql = "UPDATE users SET last_seen = ?, is_online = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, user.getLast_seen());
            stmt.setBoolean(2, user.isIs_online());
            stmt.setObject(3, user.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createUser(User user) {
        String sql = "INSERT INTO users (id, username, password, created_at, last_seen, is_online) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, user.getId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setObject(4, user.getCreated_at());
            stmt.setObject(5, user.getLast_seen());
            stmt.setBoolean(6, user.isIs_online());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    (UUID) rs.getObject("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    (Timestamp) rs.getObject("created_at"),
                    (Timestamp) rs.getObject("last_seen"),
                    rs.getBoolean("is_online")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
