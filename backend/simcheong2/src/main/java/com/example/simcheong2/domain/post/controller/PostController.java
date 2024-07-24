package com.example.simcheong2.domain.post.controller;

import com.example.simcheong2.domain.post.controller.reqeust.PostContentRequest;
import com.example.simcheong2.domain.post.controller.response.FeedResponse;
import com.example.simcheong2.domain.post.service.PostCreateService;
import com.example.simcheong2.domain.post.service.PostSearchService;
import com.example.simcheong2.domain.user_post_like.controller.request.LikeRequest;
import com.example.simcheong2.domain.user_post_like.service.LikeService;
import com.example.simcheong2.global.ai.OpenAiHelper;
import com.example.simcheong2.global.s3.S3Uploader;
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

    private final OpenAiHelper openAiHelper;
    private final S3Uploader s3Uploader;

    // 메인 피드
    @GetMapping("/main")
    public ResponseEntity<List<FeedResponse>> mainPosts() {
        return ResponseEntity.ok(new ArrayList<>());
    }

    // 추천 피드
    @GetMapping("/recommend")
    public ResponseEntity<List<FeedResponse>> recommendPosts() {
        return ResponseEntity.ok(new ArrayList<>());
    }

    //게시글등록
    @PostMapping
    public ResponseEntity<Boolean> createPost(
            HttpServletRequest servletRequest,
            @RequestPart List<MultipartFile> images,
            @RequestPart @Valid PostContentRequest request) {
        try {
            String s3Url = s3Uploader.uploadFile(images.get(0), "static");
            String uploadDirRealPath = servletRequest.getSession().getServletContext().getRealPath("/upload/"); // 저장 디렉토리 경로
            String text = openAiHelper.getTextFromMultipartFile(images.get(0), uploadDirRealPath);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }

    // 게시글 좋아요
    @PostMapping("/like")
    public ResponseEntity<Boolean> likePost(@RequestBody @Valid LikeRequest request) {
        return ResponseEntity.ok(true);
    }
}
