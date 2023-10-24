package com.eiben.Blog.App.repository;

import com.eiben.Blog.App.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByPostIdAndUserId(Long aLong, Long aLong1);

    List<Like> findByPostId(Long aLong);

    List<Like> findByUserId(Long aLong);
}
