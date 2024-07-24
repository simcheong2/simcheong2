package com.example.simcheong2.domain.follow.controller;

import com.example.simcheong2.domain.follow.controller.request.FollowNicknameRequest;
import com.example.simcheong2.domain.follow.controller.request.OtherFollowRequest;
import com.example.simcheong2.domain.follow.controller.response.FollowUserInfoResponse;
import com.example.simcheong2.domain.follow.controller.response.FollowerUserInfoResponse;
import com.example.simcheong2.domain.follow.controller.response.OtherFollowUserInfoResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "팔로우 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {
    @PostMapping
    public ResponseEntity<Boolean> followUser(@RequestBody @Valid FollowNicknameRequest request){
        return ResponseEntity.ok(true);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> unfollowUser(@RequestBody @Valid FollowNicknameRequest request){
        return ResponseEntity.ok(true);
    }

    //내 팔로우 목록
    @PostMapping("/my-follows")
    public ResponseEntity<List<FollowUserInfoResponse>> getMyFollows(){
        return ResponseEntity.ok(new ArrayList<>());
    }
    @PostMapping("/my-followers")
    public ResponseEntity<List<FollowerUserInfoResponse>> getMyFollowers(){
        return ResponseEntity.ok(new ArrayList<>());
    }
    @PostMapping("/other-follows")
    public ResponseEntity<List<OtherFollowUserInfoResponse>> getOtherFollows(
            @RequestBody @Valid OtherFollowRequest request){
        return ResponseEntity.ok(new ArrayList<>());
    }
    @PostMapping("/other-followers")
    public ResponseEntity<List<OtherFollowUserInfoResponse>> getOtherFollowers(
            @RequestBody @Valid OtherFollowRequest request){
        return ResponseEntity.ok(new ArrayList<>());
    }
}