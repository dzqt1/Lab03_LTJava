package com.solipsism.server.service;

import com.solipsism.server.model.Conversation;
import com.solipsism.server.model.User;
import com.solipsism.server.dao.ConversationDAO;
import java.util.List;
import java.sql.Timestamp;
import java.util.UUID;

public class ConvService {
    ConversationDAO convDAO;

    public ConvService() {
        this.convDAO = new ConversationDAO();
    }

    public boolean createConversation(String name, List<User> participants) {
        Conversation conv = new Conversation(UUID.randomUUID(), name, Timestamp.valueOf(java.time.LocalDateTime.now()), participants.get(0).getId(), "group");
        return convDAO.createConversation(conv);
    }

    public List<Conversation> getConversationsByUserId(UUID userId) {
        return convDAO.getConversationsByUserId(userId);
    }

    public Conversation getConversationById(UUID id) {
        return convDAO.getConversationById(id);
    }

    public boolean addParticipantToConversation(UUID conversationId, UUID userId) {
        return convDAO.addParticipant(conversationId, userId);
    }

    public boolean removeConversation(UUID conversationId) {
        return convDAO.removeConversation(conversationId);
    }
}
