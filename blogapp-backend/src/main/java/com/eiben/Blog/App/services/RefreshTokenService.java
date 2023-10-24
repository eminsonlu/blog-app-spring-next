package com.eiben.Blog.App.services;

import com.eiben.Blog.App.entities.RefreshToken;
import com.eiben.Blog.App.entities.User;
import com.eiben.Blog.App.repository.RefreshTokenRespository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private RefreshTokenRespository refreshTokenRespository;
    @Value("${app.refresh.expiration}")
    Long refreshExpiry;

    public RefreshTokenService(RefreshTokenRespository refreshTokenRespository) {
        this.refreshTokenRespository = refreshTokenRespository;
    }

    public String createRefreshToken(User user) {
        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Date.from(Instant.now().plusSeconds(refreshExpiry)));
        refreshTokenRespository.save(token);
        return token.getToken();
    }

    public boolean isRefreshExpired(RefreshToken token) {
        return token.getExpiryDate().before(new Date());
    }

    public RefreshToken getByUser(Long userId) {
        return refreshTokenRespository.findByUserId(userId);
    }
}
