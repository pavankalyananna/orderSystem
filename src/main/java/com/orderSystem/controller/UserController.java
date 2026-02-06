package com.orderSystem.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderSystem.entity.User;
import com.orderSystem.exception.BadRequestException;
import com.orderSystem.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {

        userRepo.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new BadRequestException("Email already exists");
                });

        return userRepo.save(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {

        return userRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    @GetMapping("/email/{email}")
    public User getByEmail(@PathVariable String email) {

        return userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    @GetMapping
    public List<User> getAllUsers() {

        return userRepo.findAll();
    }
}
