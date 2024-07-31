package com.example.simcheong2.domain.post.controller;

import com.example.simcheong2.domain.post.controller.reqeust.PostContentRequest;
import com.example.simcheong2.domain.post.controller.response.FeedResponse;
import com.example.simcheong2.domain.post.entity.dto.PostFeedDTO;
import com.example.simcheong2.domain.post.service.PostCreateService;
import com.example.simcheong2.domain.post.service.PostSearchService;
import com.example.simcheong2.domain.user_post_like.controller.request.LikeRequest;
import com.example.simcheong2.domain.user_post_like.service.LikeService;
import com.example.simcheong2.global.service.SecurityUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "게시글 관련 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {
    private final LikeService likeService;
    private final PostCreateService postCreateService;
    private final PostSearchService postSearchService;

    // 메인 피드
    @GetMapping("/main")
    public ResponseEntity<List<FeedResponse>> mainPosts() {
        int userId = SecurityUtil.getCurrentUserId();
//        int userId =
        // 1번 유저가 지금 피드들 요청한거임.
        List<FeedResponse> feeds = postSearchService.getFeeds(userId).stream()
                .map(PostFeedDTO::toResponse)
                .toList();

        return ResponseEntity.ok(feeds);
    }

    // 추천 피드
    @GetMapping("/random")
    public ResponseEntity<List<FeedResponse>> recommendPosts() {
        List<FeedResponse> feeds = postSearchService.getRecommendFeeds().stream()
                .map(PostFeedDTO::toResponse)
                .toList();
        return ResponseEntity.ok(feeds);
    }

    //게시글등록
    @PostMapping
    public ResponseEntity<Boolean> createPost(
            HttpServletRequest servletRequest,
            @RequestPart List<MultipartFile> images,
            @RequestPart @Valid PostContentRequest request) {
        int userId = SecurityUtil.getCurrentUserId();
        String uploadDirRealPath = servletRequest.getSession().getServletContext().getRealPath("/upload/"); // 저장 디렉토리 경로
        postCreateService.createPost(userId, images, request.getContent(), uploadDirRealPath);
        return ResponseEntity.ok(true);
    }

    // 게시글 좋아요 / 좋아요 취소. Restful 하지 않으나, 프론트와 논의 끝에 개발 편의성으로 이렇게 정함
    @PostMapping("/like")
    public ResponseEntity<Boolean> likePost(@RequestBody @Valid LikeRequest request) {
        int userId = SecurityUtil.getCurrentUserId();
        int postId = Math.toIntExact(request.getId()); // 지금 엔티티 id 타입이 인티저임. 나중에 한 번에 수정해야할듯.
        likeService.likePost(userId, postId);
        return ResponseEntity.ok(true);
    }
}
