package com.eiben.Blog.App.requests;

import lombok.Data;

@Data
public class LoginRequest {
    String name;
    String password;
}
