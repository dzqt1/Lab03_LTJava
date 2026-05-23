package com.solipsism.server.model;

import java.util.UUID;
import java.sql.Timestamp;

public class User {
    UUID id;
    String username;
    String password;
    Timestamp created_at;
    Timestamp last_seen;
    boolean is_online;

    public User() {
    }

    public User(UUID id, String username, String password, Timestamp created_at, Timestamp last_seen, boolean is_online) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.created_at = created_at;
        this.last_seen = last_seen;
        this.is_online = is_online;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(Timestamp last_seen) {
        this.last_seen = last_seen;
    }

    public boolean isIs_online() {
        return is_online;
    }

    public void setIs_online(boolean is_online) {
        this.is_online = is_online;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", created_at=" + created_at +
                ", last_seen=" + last_seen +
                ", is_online=" + is_online +
                '}';
    }
}
