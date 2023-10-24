package com.eiben.Blog.App.repository;

import com.eiben.Blog.App.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRespository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByUserId(Long userId);
}
