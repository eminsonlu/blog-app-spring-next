package com.eiben.Blog.App.controllers;

import com.eiben.Blog.App.entities.Comment;
import com.eiben.Blog.App.requests.CommentCreateRequest;
import com.eiben.Blog.App.requests.CommentUpdateRequest;
import com.eiben.Blog.App.responses.CommentResponse;
import com.eiben.Blog.App.services.CommentService;
import org.springframework.http.ResponseEntity;
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
    public List<CommentResponse> getAllComments(){
        return commentService.getAllComments();
    }
    @GetMapping("/{postId}/{userId}")
    public List<CommentResponse> getAllCommentsByPostIdAndUserId(@PathVariable Long postId, @PathVariable Long userId){
        return commentService.getAllCommntsBasedOnPostAndUser(postId, userId);
    }

    @GetMapping("/by-post/{postId}")
    public List<CommentResponse> getAllCommentsByPostId(@PathVariable Long postId){
        return commentService.getAllCommentsByPostId(postId);
    }

    @GetMapping("/by-user/{userId}")
    public List<CommentResponse> getAllCommentsByUserId(@PathVariable Long userId){
        return commentService.getAllCommentsByUserId(userId);
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
    public ResponseEntity<Void> updateOneComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest comment) {
         commentService.updateOneComment(commentId, comment);
         return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasRole('USER')")
    public void deleteOneComment(@PathVariable Long commentId) {
        commentService.deleteOneComment(commentId);
    }

}
