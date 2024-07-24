package com.example.simcheong2.domain.user_post_like.service;
import com.example.simcheong2.domain.user_post_like.repository.UserPostLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {
    private final UserPostLikeRepository userPostLikeRepository;
}
