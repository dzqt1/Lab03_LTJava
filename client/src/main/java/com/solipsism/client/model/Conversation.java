package com.solipsism.client.model;

import java.util.UUID;
import java.sql.Timestamp;

public class Conversation {
    UUID id;
    Timestamp created_at;
    UUID created_by;
    String name;
    String type;

    public Conversation() {
    }

    public Conversation(UUID id, String name, Timestamp created_at, UUID created_by, String type) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.created_by = created_by;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public UUID getCreated_by() {
        return created_by;
    }

    public void setCreated_by(UUID created_by) {
        this.created_by = created_by;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
