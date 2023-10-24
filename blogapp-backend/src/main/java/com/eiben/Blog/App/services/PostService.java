package com.eiben.Blog.App.services;

import com.eiben.Blog.App.entities.Post;
import com.eiben.Blog.App.entities.User;
import com.eiben.Blog.App.repository.PostRepository;
import com.eiben.Blog.App.requests.PostCreateRequest;
import com.eiben.Blog.App.requests.PostUpdateRequest;
import com.eiben.Blog.App.responses.LikeResponse;
import com.eiben.Blog.App.responses.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserService userService;
    private LikeService likeService;

    public PostService(PostRepository postRepository, UserService userService,@Lazy LikeService likeService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.likeService = likeService;
    }

    public List<PostResponse> getAllPosts(Optional<Long> userId) {
        List<Post> list;
        if (userId.isPresent()) list = postRepository.findByUserId(userId.get());
        else list = postRepository.findAll();
        return list.stream().map(p -> {
            List<LikeResponse> li = likeService.getAllLikes(Optional.of(p.getId()), Optional.empty());
            return new PostResponse(p, li);
        }).collect(Collectors.toList());
    }

    public Post getOnePostWithPostId(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public PostResponse getOnePostById(Long postId) {
        Post foundedPost = postRepository.findById(postId).orElse(null);
        if(foundedPost == null) return null;
        List<LikeResponse> li = likeService.getAllLikes(Optional.of(postId), Optional.empty());
        return new PostResponse(foundedPost, li);
    }

    public Post saveOnePost(PostCreateRequest post) {
        User user = userService.getOneUser(post.getUserId());
        if(user == null) return null;
        Post toSave = new Post();
        toSave.setText(post.getText());
        toSave.setTitle(post.getTitle());
        toSave.setUser(user);
        toSave = postRepository.save(toSave);
        return toSave;
    }

    public PostResponse updateOnePost(Long postId, PostUpdateRequest post) {
        Optional<Post> foundedPost = postRepository.findById(postId);
        if(foundedPost.isPresent()) {
            Post toUpdate = foundedPost.get();
            toUpdate.setText(post.getText());
            toUpdate.setTitle(post.getTitle());
            postRepository.save(toUpdate);
            List<LikeResponse> li = likeService.getAllLikes(Optional.of(postId), Optional.empty());
            return new PostResponse(toUpdate, li);
        }
        return null;
    }

    public void deleteOnePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
