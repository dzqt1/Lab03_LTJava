package com.solipsism.server.dao;

import java.sql.*;
import java.util.*;
import com.solipsism.server.util.DBConnection;
import com.solipsism.server.model.Conversation;
import java.sql.Timestamp;

public class ConversationDAO {
    
    public ConversationDAO() {
    }

    public boolean createConversation(Conversation conversation) {
        String sql = "INSERT INTO conversations (id, created_at, created_by, name, type) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, conversation.getId());
            stmt.setObject(2, conversation.getCreated_at());
            stmt.setObject(3, conversation.getCreated_by());
            stmt.setObject(4, conversation.getName());
            stmt.setObject(5, conversation.getType());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Conversation> getConversationsByUserId(UUID userId) {
        List<Conversation> conversations = new ArrayList<>();
        String sql = "SELECT c.* FROM conversations c JOIN conversation_participants cp ON c.id = cp.conversation_id WHERE cp.user_id = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                conversations.add(new Conversation(
                    (UUID) rs.getObject("id"),
                    rs.getString("name"),
                    rs.getTimestamp("created_at"),
                    (UUID) rs.getObject("created_by"),
                    rs.getString("type")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conversations;
    }

    public Conversation getConversationById(UUID id) {
        String sql = "SELECT * FROM conversations WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Conversation(
                    (UUID) rs.getObject("id"),
                    rs.getString("name"),
                    rs.getTimestamp("created_at"),
                    (UUID) rs.getObject("created_by"),
                    rs.getString("type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addParticipant(UUID conversationId, UUID userId) {
        String sql = "INSERT INTO conversation_participants (user_id, conversation_id, joined_at) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, userId);
            stmt.setObject(2, conversationId);
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
