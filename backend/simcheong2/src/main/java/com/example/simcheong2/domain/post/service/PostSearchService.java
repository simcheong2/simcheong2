package com.example.simcheong2.domain.post.service;

import com.example.simcheong2.domain.post.entity.Post;
import com.example.simcheong2.domain.post.entity.dto.PostFeedDTO;
import com.example.simcheong2.domain.post.repository.PostRepository;
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
public class PostSearchService {
    private final PostRepository postRepository;

    public List<PostFeedDTO> getFeeds(int userId) {
        return postRepository.searchPostsExceptUserId(userId)
                .orElseGet(ArrayList::new)
                .stream()
                .map(feed -> PostFeedDTO.from(feed, userId))
                .collect(Collectors.toList());
    }
}
