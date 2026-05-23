package com.solipsism.server.service;

import java.util.*;
import com.solipsism.server.dao.UserDAO;
import com.solipsism.server.model.User;
import java.time.LocalDateTime;
import java.sql.Timestamp;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public User login(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            user.setIs_online(true);
            user.setLast_seen(Timestamp.valueOf(LocalDateTime.now()));
            userDAO.saveUser(user);
            return user;
        }
        return null;
    }

    public boolean logout(String username) {
        User user = userDAO.getUserByUsername(username);
        if (user != null) {
            user.setIs_online(false);
            user.setLast_seen(Timestamp.valueOf(LocalDateTime.now()));
            return userDAO.saveUser(user);
        }
        return false;
    }

    public boolean register(String username, String password) {
        if (userDAO.getUserByUsername(username) != null) {
            return false;
        }
        User newUser = new User(UUID.randomUUID(), username, password, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), false);
        return userDAO.createUser(newUser);
    }
}
