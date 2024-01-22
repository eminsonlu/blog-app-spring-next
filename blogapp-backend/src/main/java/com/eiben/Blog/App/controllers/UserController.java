package com.eiben.Blog.App.controllers;

import com.eiben.Blog.App.controllers.dto.request.UserUpdateRequest;
import com.eiben.Blog.App.controllers.dto.response.AbstractBaseResponse;
import com.eiben.Blog.App.controllers.dto.response.UserResponse;
import com.eiben.Blog.App.entities.User;
import com.eiben.Blog.App.repository.UserRepository;
import com.eiben.Blog.App.services.UserService;
import org.springframework.validation.annotation.Validated;
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
    public AbstractBaseResponse<List<UserResponse>> getUsers() {
        return new AbstractBaseResponse<>(userService.getAllUsers(), null);
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
    public User updateOneUser(@PathVariable Long userId, @Validated @RequestBody UserUpdateRequest nuser) {
        return userService.updateOneUser(userId, nuser);
    }

    @DeleteMapping("/{userId}")
    public void deleteOneUser(@PathVariable Long userId) {
        userService.deleteOneUser(userId);
    }
}
