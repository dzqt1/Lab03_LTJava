package com.solipsism.server.dao;

import java.sql.*;
import java.util.*;
import com.solipsism.server.util.DBConnection;
import com.solipsism.server.model.Message;

public class MessageDAO {
    public MessageDAO() {
    }

    public boolean createMessage(Message message) {
        String sql = "INSERT INTO messages (id, conversation_id, sender_id, content, created_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, message.getId());
            stmt.setObject(2, message.getConversation_id());
            stmt.setObject(3, message.getSender_id());
            stmt.setObject(4, message.getContent());
            stmt.setObject(5, message.getCreated_at());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Message> getMessagesByConversationId(UUID conversationId) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE conversation_id = ? ORDER BY created_at ASC";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, conversationId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                messages.add(new Message(
                    (UUID) rs.getObject("id"),
                    (UUID) rs.getObject("conversation_id"),
                    (UUID) rs.getObject("sender_id"),
                    rs.getString("content"),
                    rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

}