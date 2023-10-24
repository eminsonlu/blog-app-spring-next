package com.eiben.Blog.App.controllers;

import com.eiben.Blog.App.entities.Comment;
import com.eiben.Blog.App.requests.CommentCreateRequest;
import com.eiben.Blog.App.requests.CommentUpdateRequest;
import com.eiben.Blog.App.responses.CommentResponse;
import com.eiben.Blog.App.services.CommentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentResponse> getAllComments(@RequestParam Optional<Long> postId, @RequestParam Optional<Long> userId){
        return commentService.getAllComments(postId, userId);
    }

    @GetMapping("/{commentId}")
    @PreAuthorize("hasRole('USER')")
    public CommentResponse getOneComment(@PathVariable Long commentId){
        return commentService.getOneComment(commentId);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public CommentResponse createOneComment(@RequestBody CommentCreateRequest comment){
        return commentService.saveOneComment(comment);
    }

    @PutMapping("/{commentId}")
    @PreAuthorize("hasRole('USER')")
    public CommentResponse updateOneComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest comment) {
        return commentService.updateOneComment(commentId, comment);
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasRole('USER')")
    public void deleteOneComment(@PathVariable Long commentId) {
        commentService.deleteOneComment(commentId);
    }

}
