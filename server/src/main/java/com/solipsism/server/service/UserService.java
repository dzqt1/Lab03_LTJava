package com.solipsism.server.service;

import java.util.*;
import com.solipsism.server.dao.UserDAO;
import com.solipsism.server.model.User;
import com.solipsism.server.util.PasswordUtil;

import java.time.LocalDateTime;
import java.sql.Timestamp;

public class UserService {
    private UserDAO userDAO;
    private PasswordUtil passwordUtil;

    public UserService() {
        this.userDAO = new UserDAO();
        this.passwordUtil = new PasswordUtil();
    }

    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public User login(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && passwordUtil.verifyPassword(password, user.getPassword())) {
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
        String hashedPassword = passwordUtil.hashPassword(password);
        User newUser = new User(UUID.randomUUID(), username, hashedPassword, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), false);
        return userDAO.createUser(newUser);
    }
}
