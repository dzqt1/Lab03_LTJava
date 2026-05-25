package com.solipsism.client.service;

import com.solipsism.client.model.User;
import java.util.UUID;

public class SessionService {
    private static SessionService instance;
    private User currentUser;
    private boolean isLoggedIn = false;

    private SessionService() {
    }

    public static SessionService getInstance() {
        if (instance == null) {
            instance = new SessionService();
        }
        return instance;
    }

    public void login(UUID userId, String username) {
        this.currentUser = new User(userId, username, true);
        this.isLoggedIn = true;
    }

    public void logout() {
        if (currentUser != null) {
            currentUser.setIs_online(false);
        }
        this.currentUser = null;
        this.isLoggedIn = false;
    }

    public boolean isLoggedIn() {
        return isLoggedIn && currentUser != null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public UUID getCurrentUserId() {
        return currentUser != null ? currentUser.getId() : null;
    }

    public String getCurrentUsername() {
        return currentUser != null ? currentUser.getUsername() : null;
    }
}
