package com.eiben.Blog.App.repository;

import com.eiben.Blog.App.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String username);
}
