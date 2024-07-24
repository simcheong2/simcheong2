package com.example.simcheong2.domain.post.controller;

import com.example.simcheong2.domain.post.controller.reqeust.PostContentRequest;
import com.example.simcheong2.domain.post.controller.response.FeedResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<Boolean> createPost (
            @RequestPart List < MultipartFile > images,
            @RequestPart @Valid PostContentRequest request){
        return ResponseEntity.ok(true);
    }
}
