package com.eiben.Blog.App.requests;

import lombok.Data;

@Data
public class RegisterRequest {
    String name;
    String email;
    String password;
}
