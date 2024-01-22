package com.eiben.Blog.App.services;

import com.eiben.Blog.App.entities.Comment;
import com.eiben.Blog.App.repository.CommentRepository;
import com.eiben.Blog.App.requests.CommentCreateRequest;
import com.eiben.Blog.App.requests.CommentUpdateRequest;
import com.eiben.Blog.App.responses.CommentResponse;
import javax.swing.text.html.Option;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<CommentResponse> getAllComments() {
        List<Comment> list = commentRepository.findAll();
        return list.stream().map(c -> new CommentResponse(c)).collect(Collectors.toList());
    }

    public CommentResponse getOneComment(Long commentId) {
        Comment com = commentRepository.findById(commentId).orElse(null);
        if(com == null) return null;
        return new CommentResponse(com);
    }

    public CommentResponse saveOneComment(CommentCreateRequest comment) {
        // check if userId and postId are valid
        if(userService.getOneUser(comment.getUserId()) == null) return null;
        if(postService.getOnePostById(comment.getPostId()) == null) return null;
        // create new comment
        Comment toSave = new Comment();
        toSave.setText(comment.getText());
        toSave.setUser(userService.getOneUser(comment.getUserId()));
        toSave.setPost(postService.getOnePostWithPostId(comment.getPostId()));
        toSave = commentRepository.save(toSave);
        return new CommentResponse(toSave);
    }

    public void updateOneComment(Long commentId, CommentUpdateRequest comment) {
        Optional<Comment> foundedComment = commentRepository.findById(commentId);
        if (foundedComment.isEmpty()) {
            return;
        }
        Comment toUpdate = foundedComment.get();
        toUpdate.setText(comment.getText());
        commentRepository.save(toUpdate);
    }

    public void deleteOneComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<CommentResponse> getAllCommntsBasedOnPostAndUser(Long postId, Long userId) {
        List<Comment> list = commentRepository.findByPostIdAndUserId(postId, userId);
        return list.stream().map(c -> new CommentResponse(c)).collect(Collectors.toList());
    }

    public List<CommentResponse> getAllCommentsByPostId(Long postId) {
        List<Comment> list = commentRepository.findByPostId(postId);
        return list.stream().map(c -> new CommentResponse(c)).collect(Collectors.toList());
    }

    public List<CommentResponse> getAllCommentsByUserId(Long userId) {
        List<Comment> list = commentRepository.findByUserId(userId);
        return list.stream().map(c -> new CommentResponse(c)).collect(Collectors.toList());
    }
}
