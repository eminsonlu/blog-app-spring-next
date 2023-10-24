package com.eiben.Blog.App.responses;

import com.eiben.Blog.App.entities.Like;
import lombok.Data;

@Data
public class LikeResponse {
    Long id;
    Long userId;
    Long postId;

    public LikeResponse(Like like){
        this.id = like.getId();
        this.userId = like.getUser().getId();
        this.postId = like.getPost().getId();
    }
}
