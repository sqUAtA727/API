package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.UserRequest;
import com.example.demo.entity.User;
import com.example.demo.repo.RoleRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public UserService(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public User addUser(User user) {
        return userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User register(UserRequest request) {
        log.info("Register request: {}", request);

        if (userRepo.existsByUsername(request.getUsername())) {
            log.warn("Username already exists: {}", request.getUsername());
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepo.existsByEmail(request.getEmail())) {
            log.warn("Email already exists: {}", request.getEmail());
            throw new IllegalArgumentException("Email already exists");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());
        user.setRoleId(3L);

        log.info("User registered successfully: {}", user.getId());

        return userRepo.save(user);
    }

    public String login(LoginRequest request) {
        log.info("Login request: {}", request.getUsername());

        User user = userRepo.findByUsernameAndPassword(request.getUsername(), request.getPassword());

        if (user == null) {
            log.warn("Login failed for username: {}", request.getUsername());
            throw new IllegalArgumentException("Invalid username or password");
        }

        String token = JwtUtil.generateToken(user);

        log.info("User logged in successfully: {}", user.getUsername());

        return token;
    }

    public User getUser(String token) {
        try {
            String username = JwtUtil.getUsernameFromToken(token);
            return userRepo.findByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
    }
}
