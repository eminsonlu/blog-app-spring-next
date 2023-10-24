package com.eiben.Blog.App.services;

import com.eiben.Blog.App.entities.User;
import com.eiben.Blog.App.repository.UserRepository;
import jakarta.validation.constraints.Email;
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


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveOneUser(User nuser) {
        Optional<User> saved = Optional.of(userRepository.save(nuser));

        saved.ifPresent(u -> {
            if (u.getIs_verified()){
                return;
            }

           try {
               String token = UUID.randomUUID().toString();
               userVerificationService.save(saved.get(), token);
               // send email
               emailService.sendHtmlMail(u, token);
           }catch (Exception e) {
               e.printStackTrace();
           }
        });

        return saved.get();
    }

    public User getOneUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateOneUser(Long userId, User nuser) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()) {
            // we have declare user as optional so we can use it here with get method if user present
            User foundedUser = user.get();
            foundedUser.setName(nuser.getName());
            foundedUser.setEmail(nuser.getEmail());
            foundedUser.setPassword(nuser.getPassword());
            userRepository.save(foundedUser);
            return foundedUser;
        }else {
            // custom exception
            return null;
        }
    }

    public void deleteOneUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User getOneUserByName(String name) {
        return userRepository.findByName(name);
    }
}
