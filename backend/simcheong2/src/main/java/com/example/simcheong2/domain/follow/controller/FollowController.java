package com.example.simcheong2.domain.follow.controller;

import com.example.simcheong2.domain.follow.controller.request.FollowNicknameRequest;
import com.example.simcheong2.domain.follow.controller.request.OtherFollowRequest;
import com.example.simcheong2.domain.follow.controller.response.FollowUserInfoResponse;
import com.example.simcheong2.domain.follow.controller.response.FollowerUserInfoResponse;
import com.example.simcheong2.domain.follow.controller.response.OtherFollowUserInfoResponse;
import com.example.simcheong2.domain.follow.entity.dto.FollowUserInfoDTO;
import com.example.simcheong2.domain.follow.entity.dto.FollowerUserInfoDTO;
import com.example.simcheong2.domain.follow.entity.dto.OtherFollowUserInfoDTO;
import com.example.simcheong2.domain.follow.service.FollowSearchService;
import com.example.simcheong2.domain.follow.service.FollowUpdateService;
import com.example.simcheong2.global.service.SecurityUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "팔로우 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {
    private final FollowUpdateService followUpdateService;
    private final FollowSearchService followSearchService;

    @PostMapping
    public ResponseEntity<Boolean> followUser(@RequestBody @Valid FollowNicknameRequest request) {
        int userId = SecurityUtil.getCurrentUserId();
        followUpdateService.follow(userId, request.getNickname());
        return ResponseEntity.ok(true);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> unfollowUser(@RequestBody @Valid FollowNicknameRequest request) {
        int userId = SecurityUtil.getCurrentUserId();
        followUpdateService.unfollow(userId, request.getNickname());
        return ResponseEntity.ok(true);
    }

    //내 팔로우 목록
    @GetMapping("/my-follows")
    public ResponseEntity<List<FollowUserInfoResponse>> getMyFollows() {
        int userId = SecurityUtil.getCurrentUserId();
        List<FollowUserInfoDTO> follow = followSearchService.searchMyFollows(userId);
        return ResponseEntity.ok(follow.stream()
                .map(FollowUserInfoDTO::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/my-followers")
    public ResponseEntity<List<FollowerUserInfoResponse>> getMyFollowers() {
        int userId = SecurityUtil.getCurrentUserId();
        List<FollowerUserInfoDTO> followers = followSearchService.searchMyFollowers(userId);
        return ResponseEntity.ok(followers.stream()
                .map(FollowerUserInfoDTO::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/other-follows")
    public ResponseEntity<List<OtherFollowUserInfoResponse>> getOtherFollows(
            @RequestBody @Valid OtherFollowRequest request) {
        int userId = SecurityUtil.getCurrentUserId();
        List<OtherFollowUserInfoDTO> otherFollows = followSearchService.searchOtherFollows(userId, request.getNickname());
        return ResponseEntity.ok(otherFollows.stream()
                .map(OtherFollowUserInfoDTO::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/other-followers")
    public ResponseEntity<List<OtherFollowUserInfoResponse>> getOtherFollowers(
            @RequestBody @Valid OtherFollowRequest request) {
        return ResponseEntity.ok(new ArrayList<>());
    }
}
