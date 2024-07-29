package com.example.simcheong2.domain.follow.service;

import com.example.simcheong2.domain.follow.entity.dto.FollowUserInfoDTO;
import com.example.simcheong2.domain.follow.entity.dto.FollowerUserInfoDTO;
import com.example.simcheong2.domain.follow.entity.dto.OtherFollowUserInfoDTO;
import com.example.simcheong2.domain.follow.entity.dto.OtherFollowerUserInfoDTO;
import com.example.simcheong2.domain.follow.repository.FollowRepository;
import com.example.simcheong2.domain.user.entity.User;
import com.example.simcheong2.domain.user.repository.UserRepository;
import com.example.simcheong2.domain.user.service.UserSearchService;
import com.example.simcheong2.global.exception.model.CustomException;
import com.example.simcheong2.global.exception.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FollowSearchService {
    private final UserSearchService userSearchService;
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public List<FollowUserInfoDTO> searchMyFollows(int userId) {
        return userRepository.getFollows(userId)
                .orElseGet(ArrayList::new)
                .stream()
                .map(FollowUserInfoDTO::from)
                .collect(Collectors.toList());
    }

    public List<FollowerUserInfoDTO> searchMyFollowers(int userId) {
        User me = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "유저가 존재하지 않습니다"));
        return userRepository.getFollowers(userId)
                .orElseGet(ArrayList::new)
                .stream()
                .map(user -> FollowerUserInfoDTO.from(me, user))
                .collect(Collectors.toList());
    }

    public List<OtherFollowUserInfoDTO> searchOtherFollows(int userId, String otherNickname) {
        User me = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "유저가 존재하지 않습니다. 잠시 후 다시 시도해주세요."));
        User other = userRepository.findByNickname(otherNickname)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "유저가 존재하지 않습니다. 잠시 후 다시 시도해주세요."));

        return userRepository.getFollows(other.getUserId())
                .orElseGet(ArrayList::new)
                .stream()
                .map(user -> OtherFollowUserInfoDTO.from(me, user))
                .collect(Collectors.toList());
    }

    public List<OtherFollowerUserInfoDTO> searchOtherFollowers(int userId, String otherNickname) {
        User me = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "유저가 존재하지 않습니다. 잠시 후 다시 시도해주세요."));
        User other = userRepository.findByNickname(otherNickname)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "유저가 존재하지 않습니다. 잠시 후 다시 시도해주세요."));

        return userRepository.getFollowers(other.getUserId())
                .orElseGet(ArrayList::new)
                .stream()
                .map(user -> OtherFollowerUserInfoDTO.from(me, user))
                .collect(Collectors.toList());
    }
}