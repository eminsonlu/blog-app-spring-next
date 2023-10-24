package com.eiben.Blog.App.requests;

import lombok.Data;

@Data
public class CommentCreateRequest {
    String text;
    Long postId;
    Long userId;
}
