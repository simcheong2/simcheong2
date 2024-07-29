package com.example.simcheong2.domain.follow.service;

import com.example.simcheong2.domain.follow.entity.Follow;
import com.example.simcheong2.domain.follow.repository.FollowRepository;
import com.example.simcheong2.domain.user.entity.User;
import com.example.simcheong2.domain.user.repository.UserRepository;
import com.example.simcheong2.global.exception.model.CustomException;
import com.example.simcheong2.global.exception.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FollowUpdateService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public void follow(int followerId, String followingNickname) {
        User followerUser = userRepository.findByUserId(followerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "유저가 존재하지 않습니다. 확인 후 다시 요청해주세요."));

        User followingUser = userRepository.findByNickname(followingNickname)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "유저가 존재하지 않습니다. 확인 후 다시 요청해주세요."));

        Optional<Follow> follow = followRepository.findByFollowerAndFollowing(followerUser, followingUser);
        if (follow.isPresent()) {
            log.info("팔로우 이미 되어 있음");
            throw new CustomException(ErrorCode.BAD_REQUEST, "이미 팔로우 되어 있습니다.");
        }

        log.info("팔로우");
        followRepository.save(Follow.builder()
                .follower(followerUser)
                .following(followingUser)
                .build());
    }

    public void unfollow(int followerId, String followingNickname) {
        User followerUser = userRepository.findByUserId(followerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "유저가 존재하지 않습니다. 확인 후 다시 요청해주세요."));

        User followingUser = userRepository.findByNickname(followingNickname)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "유저가 존재하지 않습니다. 확인 후 다시 요청해주세요."));

        Follow follow = followRepository.findByFollowerAndFollowing(followerUser, followingUser)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST, "팔로우가 되어 있지 않아 팔로우 취소 할 수 없습니다."));

        log.info("팔로우 취소 성공");
        followRepository.delete(follow);
    }
}
