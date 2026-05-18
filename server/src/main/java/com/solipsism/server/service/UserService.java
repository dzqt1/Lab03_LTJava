package com.solipsism.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.solipsism.server.entity.User;
import com.solipsism.server.repository.UserRepository;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User newUser) {
        if (userRepository.findByUsername(newUser.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        return userRepository.save(newUser);
    }
}
