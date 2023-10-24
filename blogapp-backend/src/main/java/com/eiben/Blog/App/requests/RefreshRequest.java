package com.eiben.Blog.App.requests;

import lombok.Data;

@Data
public class RefreshRequest {
    Long userId;
    String refreshToken;
}
