package com.example.simcheong2.domain.post.service;

import com.example.simcheong2.domain.post.entity.Post;
import com.example.simcheong2.domain.post.entity.dto.PostFeedDTO;
import com.example.simcheong2.domain.post.repository.PostRepository;
import com.example.simcheong2.global.exception.model.CustomException;
import com.example.simcheong2.global.exception.model.ErrorCode;
import com.example.simcheong2.global.service.SecurityUtil;
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

    public List<PostFeedDTO> getRecommendFeeds(){
        int userId = SecurityUtil.getCurrentUserId();
        List<Post> recentList = postRepository.findRecentPosts(userId)
                .orElseGet(ArrayList::new);
        List<PostFeedDTO> result = new ArrayList<>();
        if(recentList.isEmpty()){
            throw new CustomException(ErrorCode.BAD_REQUEST,"최근 게시물이 존재하지 않습니다.");
        }
        List<Post> sortedUniquePosts = recentList.stream()
                .sorted((post1, post2) -> Integer.compare(post2.getLike(), post1.getLike()))
                .toList();

        for(Post post : sortedUniquePosts){
            result.add(PostFeedDTO.from(post,userId));
            if(result.size() == 10){
                break;
            }
        }
        return result;
    }
}
