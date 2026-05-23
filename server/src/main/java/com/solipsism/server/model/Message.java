package com.solipsism.server.model;

import java.util.UUID;
import java.sql.Timestamp;

public class Message {
    private UUID id;
    private UUID conversation_id;
    private UUID sender_id;
    private String content;
    private Timestamp created_at;

    public Message(UUID id, UUID conversation_id, UUID sender_id, String content, Timestamp created_at) {
        this.id = id;
        this.conversation_id = conversation_id;
        this.sender_id = sender_id;
        this.content = content;
        this.created_at = created_at;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(UUID conversation_id) {
        this.conversation_id = conversation_id;
    }

    public UUID getSender_id() {
        return sender_id;
    }

    public void setSender_id(UUID sender_id) {
        this.sender_id = sender_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
