package com.solipsism.server.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.solipsism.server.entity.User;
import com.solipsism.server.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User loginRequest) {
        User user = userService.getUserByUsername(loginRequest.getUsername());
        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).build();
        }
        userService.loginUser(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(@RequestBody User logoutRequest) {
        User user = userService.getUserByUsername(logoutRequest.getUsername());
        if (user == null) {
            return ResponseEntity.status(404).build();
        }
        userService.logoutUser(user);
        return ResponseEntity.ok().build();
    }
}
