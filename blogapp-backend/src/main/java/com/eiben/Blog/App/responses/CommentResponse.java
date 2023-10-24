package com.eiben.Blog.App.responses;

import com.eiben.Blog.App.entities.Comment;
import lombok.Data;

@Data
public class CommentResponse {
    Long id;
    Long userId;
    String userName;
    Long postId;
    String text;

    public CommentResponse(Comment entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.userName = entity.getUser().getName();
        this.postId = entity.getPost().getId();
        this.text = entity.getText();
    }
}
