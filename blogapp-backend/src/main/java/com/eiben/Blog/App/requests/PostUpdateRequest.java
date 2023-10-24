package com.eiben.Blog.App.requests;

import lombok.Data;

@Data
public class PostUpdateRequest {
    String title;
    String text;
}
