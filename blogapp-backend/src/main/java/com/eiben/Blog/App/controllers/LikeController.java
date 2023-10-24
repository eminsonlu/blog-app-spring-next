package com.eiben.Blog.App.controllers;

import com.eiben.Blog.App.requests.LikeCreateRequest;
import com.eiben.Blog.App.responses.LikeResponse;
import com.eiben.Blog.App.services.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public List<LikeResponse> getAllLikes(@RequestParam Optional<Long> postId, @RequestParam Optional<Long> userId){
        return likeService.getAllLikes(postId, userId);
    }

    @GetMapping("/{likeId}")
    @PreAuthorize("hasRole('USER')")
    public LikeResponse getOneLike(@PathVariable Long likeId){
        return likeService.getOneLike(likeId);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public LikeResponse createOneLike(@RequestBody LikeCreateRequest like){
        return likeService.saveOneLike(like);
    }

    @DeleteMapping("/{likeId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteOneLike(@PathVariable Long likeId) {
        likeService.deleteOneLike(likeId);
        return ResponseEntity.ok().build();
    }


}
