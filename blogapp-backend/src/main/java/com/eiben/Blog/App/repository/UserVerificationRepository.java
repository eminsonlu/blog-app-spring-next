package com.eiben.Blog.App.repository;

import com.eiben.Blog.App.entities.User;
import com.eiben.Blog.App.entities.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVerificationRepository extends JpaRepository<UserVerification, Long> {
    UserVerification findByUser(User user);
    UserVerification findByToken(String token);
}
