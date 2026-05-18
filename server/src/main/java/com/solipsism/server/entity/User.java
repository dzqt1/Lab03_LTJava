package com.solipsism.server.entity;

import jakarta.persistence.*;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column()
    private LocalDateTime last_seen;

    @Column()
    private LocalDateTime created_at;


    // Constructors, getters, and setters

    public User() {
        created_at = LocalDateTime.now();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.created_at = LocalDateTime.now();
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

    public LocalDateTime getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(LocalDateTime last_seen) {
        this.last_seen = last_seen;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
