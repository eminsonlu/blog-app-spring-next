package com.eiben.Blog.App.requests;

import lombok.*;

@Data
public class PostCreateRequest {
    String text;
    String title;
    Long userId;
}
