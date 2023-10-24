package com.eiben.Blog.App.requests;

import lombok.Data;

@Data
public class LikeCreateRequest {
    Long postId;
    Long userId;
}
