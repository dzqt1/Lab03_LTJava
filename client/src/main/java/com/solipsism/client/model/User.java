/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.solipsism.client.model;

import java.io.Serializable;
import java.util.UUID;
import java.time.LocalDateTime;

/**
 *
 * @author Nhan Vo
 */

public class User implements Serializable {
    private UUID id;
    private String username;
    private String password;
    private boolean is_online;

    public User() {
    }

    public User(UUID id, String username, String password, LocalDateTime created_at, LocalDateTime updated_at, boolean is_online) {
        this.id = id;
        this.username = username;
        this.password = password;
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

    public boolean isIs_online() {
        return is_online;
    }

    public void setIs_online(boolean is_online) {
        this.is_online = is_online;
    }

    
    public void print() {
        System.out.println("User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", is_online=" + is_online +
                '}');    
    }
}
