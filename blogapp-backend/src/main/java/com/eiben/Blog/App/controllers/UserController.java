package com.eiben.Blog.App.controllers;

import com.eiben.Blog.App.entities.User;
import com.eiben.Blog.App.repository.UserRepository;
import com.eiben.Blog.App.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User nuser) {
        return userService.saveOneUser(nuser);
    }

    @GetMapping("/{userId}")
    public User getOneUser(@PathVariable Long userId) {
        return userService.getOneUser(userId);
    }

    @PutMapping("/{userId}")
    public User updateOneUser(@PathVariable Long userId, @RequestBody User nuser) {
        return userService.updateOneUser(userId, nuser);
    }

    @DeleteMapping("/{userId}")
    public void deleteOneUser(@PathVariable Long userId) {
        userService.deleteOneUser(userId);
    }
}
