package com.example.simcheong2.domain.follow.service;

import com.example.simcheong2.domain.follow.repository.FollowRepository;
import com.example.simcheong2.domain.user.service.UserSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FollowSearchService {
    private final UserSearchService userSearchService;
    private final FollowRepository followRepository;
}