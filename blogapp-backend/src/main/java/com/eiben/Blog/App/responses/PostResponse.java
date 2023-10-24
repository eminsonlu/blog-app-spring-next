package com.eiben.Blog.App.responses;

import com.eiben.Blog.App.entities.Post;
import lombok.Data;

import java.util.List;

@Data
public class PostResponse {
    Long id;
    Long userId;
    String userName;
    String title;
    String text;
    List<LikeResponse> likes;

    public PostResponse(Post entity, List<LikeResponse> likes) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.userName = entity.getUser().getName();
        this.title = entity.getTitle();
        this.text = entity.getText();
        this.likes = likes;
    }
}
