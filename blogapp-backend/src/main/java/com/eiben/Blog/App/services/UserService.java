package com.eiben.Blog.App.services;

import com.eiben.Blog.App.controllers.dto.request.UserUpdateRequest;
import com.eiben.Blog.App.controllers.dto.response.UserResponse;
import com.eiben.Blog.App.converter.UserConverter;
import com.eiben.Blog.App.entities.User;
import com.eiben.Blog.App.repository.UserRepository;
import jakarta.validation.constraints.Email;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    UserRepository userRepository;
    UserVerificationService userVerificationService;
    EmailService emailService;

    public UserService(UserRepository userRepository, UserVerificationService userVerificationService, EmailService emailService) {
        this.userRepository = userRepository;
        this.userVerificationService = userVerificationService;
        this.emailService = emailService;
    }

    public List<UserResponse> getAllUsers() {
        List<User> all = userRepository.findAll();
        if (all.isEmpty()) {
            throw new UsernameNotFoundException("No users found");
        }
        return all.stream().map(UserConverter::toUserResponse).collect(Collectors.toList());
    }

    public User saveOneUser(User nuser) {
        User saved = userRepository.save(nuser);
        if (saved.getVerified()){
            return null;
        }

       try {
           String token = UUID.randomUUID().toString();
           userVerificationService.save(saved, token);
           // send email
           emailService.sendHtmlMail(saved, token);
       }catch (Exception e) {
           e.printStackTrace();
       }

        return saved;
    }

    public User getOneUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateOneUser(Long userId, UserUpdateRequest updateRequest) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            // custom exception
            return null;
        }
        // we have declare user as optional so we can use it here with get method if user present
        User foundedUser = user.get();
        foundedUser.setName(updateRequest.getName());
        foundedUser.setEmail(updateRequest.getEmail());
        foundedUser.setPassword(updateRequest.getPassword());
        userRepository.save(foundedUser);
        return foundedUser;
    }

    public void deleteOneUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User getOneUserByName(String name) {
        return userRepository.findByName(name);
    }
}
