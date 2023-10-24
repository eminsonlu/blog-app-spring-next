package com.eiben.Blog.App.services;

import com.eiben.Blog.App.entities.Comment;
import com.eiben.Blog.App.repository.CommentRepository;
import com.eiben.Blog.App.requests.CommentCreateRequest;
import com.eiben.Blog.App.requests.CommentUpdateRequest;
import com.eiben.Blog.App.responses.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<CommentResponse> getAllComments(Optional<Long> postId, Optional<Long> userId) {
        List<Comment> list;
        if(postId.isPresent() && userId.isPresent()) {
            list = commentRepository.findByPostIdAndUserId(postId.get(), userId.get());
        }else if (postId.isPresent()){
            list = commentRepository.findByPostId(postId.get());
        }else if (userId.isPresent()) {
            list = commentRepository.findByUserId(userId.get());
        }else {
            list = commentRepository.findAll();
        }
        List<CommentResponse> forReturn = list.stream().map(c -> new CommentResponse(c)).collect(Collectors.toList());
        return forReturn;
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

    public CommentResponse updateOneComment(Long commentId, CommentUpdateRequest comment) {
        Optional<Comment> foundedComment = commentRepository.findById(commentId);
        if(foundedComment.isPresent()) {
            Comment toUpdate = foundedComment.get();
            toUpdate.setText(comment.getText());
            toUpdate = commentRepository.save(toUpdate);
            return new CommentResponse(toUpdate);
        }
        return null;
    }

    public void deleteOneComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
