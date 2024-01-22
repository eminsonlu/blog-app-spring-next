package com.eiben.Blog.App.controllers;

import com.eiben.Blog.App.entities.Post;
import com.eiben.Blog.App.requests.PostCreateRequest;
import com.eiben.Blog.App.requests.PostUpdateRequest;
import com.eiben.Blog.App.responses.PostResponse;
import com.eiben.Blog.App.services.PostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId) {
        return postService.getAllPosts(userId);
    }


    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public Post createOnePost(@RequestBody PostCreateRequest post) {
        return postService.saveOnePost(post);
    }

    @GetMapping("/{postId}")
    @PreAuthorize("hasRole('USER')")
    public PostResponse getOnePost(@PathVariable Long postId) {
        return postService.getOnePostById(postId);
    }

    @PutMapping("/{postId}")
    @PreAuthorize("hasRole('USER')")
    public PostResponse updateOnePost(@PathVariable Long postId, @RequestBody PostUpdateRequest post) {
        return postService.updateOnePost(postId, post);
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasRole('USER')")
    public void deleteOnePost(@PathVariable Long postId) {
        postService.deleteOnePost(postId);
    }
}
