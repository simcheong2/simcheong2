package com.example.simcheong2.domain.follow.service;

import com.example.simcheong2.domain.follow.entity.dto.FollowUserInfoDTO;
import com.example.simcheong2.domain.follow.repository.FollowRepository;
import com.example.simcheong2.domain.user.repository.UserRepository;
import com.example.simcheong2.domain.user.service.UserSearchService;
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
        return userRepository.getMyFollows(userId)
                .orElseGet(ArrayList::new)
                .stream()
                .map(FollowUserInfoDTO::from)
                .collect(Collectors.toList());
    }
}