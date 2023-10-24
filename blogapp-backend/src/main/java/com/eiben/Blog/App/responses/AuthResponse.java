package com.eiben.Blog.App.responses;

import lombok.Data;

import java.util.List;

@Data
public class AuthResponse {
    String accessToken;
    String refreshToken;
    Long id;
    String name;
    String message;
    List<String> roles;
}
