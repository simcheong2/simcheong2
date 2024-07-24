package com.example.simcheong2.domain.user.service;
import com.example.simcheong2.domain.post.service.PostSearchService;
import com.example.simcheong2.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserSearchService {
    private final UserValidationService userValidationService;
    private final UserRepository userRepository;
    private final PostSearchService postSearchService;
}
