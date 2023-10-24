package com.eiben.Blog.App.services;

import com.eiben.Blog.App.entities.Like;
import com.eiben.Blog.App.repository.LikeRepository;
import com.eiben.Blog.App.repository.PostRepository;
import com.eiben.Blog.App.repository.UserRepository;
import com.eiben.Blog.App.requests.LikeCreateRequest;
import com.eiben.Blog.App.responses.LikeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {
    private LikeRepository likeRepository;
    private PostService postService;
    private UserService userService;

    public LikeService(LikeRepository likeRepository, PostService postService, UserService userService) {
        this.likeRepository = likeRepository;
        this.postService = postService;
        this.userService = userService;
    }


    public List<LikeResponse> getAllLikes(Optional<Long> postId, Optional<Long> userId) {
        List<Like> list;
        if(postId.isPresent() && userId.isPresent()) {
            list = likeRepository.findByPostIdAndUserId(postId.get(), userId.get());
        }else if (postId.isPresent()){
            list = likeRepository.findByPostId(postId.get());
        }else if (userId.isPresent()) {
            list = likeRepository.findByUserId(userId.get());
        }else {
            list = likeRepository.findAll();
        }
        List<LikeResponse> forReturn = list.stream().map(c -> new LikeResponse(c)).collect(Collectors.toList());
        return forReturn;
    }

    public LikeResponse getOneLike(Long likeId) {
        Like foundedLike = likeRepository.findById(likeId).orElse(null);
        if(foundedLike == null) return null;
        return new LikeResponse(foundedLike);
    }

    public LikeResponse saveOneLike(LikeCreateRequest like) {
        if(userService.getOneUser(like.getUserId()) == null) return null;
        if(postService.getOnePostById(like.getPostId()) == null) return null;
        Like toSave = new Like();
        toSave.setUser(userService.getOneUser(like.getUserId()));
        toSave.setPost(postService.getOnePostWithPostId(like.getPostId()));
        toSave = likeRepository.save(toSave);
        return new LikeResponse(toSave);
    }

    public void deleteOneLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
