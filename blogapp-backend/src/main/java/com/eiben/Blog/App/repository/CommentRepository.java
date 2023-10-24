package com.eiben.Blog.App.repository;

import com.eiben.Blog.App.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdAndUserId(Long aLong, Long aLong1);

    List<Comment> findByPostId(Long aLong);

    List<Comment> findByUserId(Long aLong);
}
