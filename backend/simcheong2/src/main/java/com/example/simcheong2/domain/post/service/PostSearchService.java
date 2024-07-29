package com.example.simcheong2.domain.post.service;

import com.example.simcheong2.domain.post.entity.Post;
import com.example.simcheong2.domain.post.entity.dto.PostFeedDTO;
import com.example.simcheong2.domain.post.repository.PostRepository;
import com.example.simcheong2.global.service.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostSearchService {
    private final PostRepository postRepository;
    private final RedisTemplate redisTemplate;

    public List<PostFeedDTO> getFeeds(int userId) {
        return postRepository.searchPostsExceptUserId(userId)
                .orElseGet(ArrayList::new)
                .stream()
                .map(feed -> PostFeedDTO.from(feed, userId))
                .collect(Collectors.toList());
    }

    public List<PostFeedDTO> getRecommendFeeds(){
        // 1. Post 가 왜래 키로 참조하는 user 의 postVisible = true 인 post 들을 가져옴
        // 2. 해당 post 들을 create_date 순으로 정렬하여 n개를 선택함..(가장 최근것이 먼저 오도록)
        // 3. 레디스에, 해당 게시글의 post id 를 일정시간 저장함. 만약 2 과정에서 선택한 게시글의 post id가
        // 레디스에 이미 저장되어 있을 경우, 해당 post 대신 다른 상위 post를 가져온다.
        int userId = SecurityUtil.getCurrentUserId();
        List<Post> recentList = postRepository.findRecentPosts(userId)
                .orElseGet(ArrayList::new);
        List<PostFeedDTO> result = new ArrayList<>();
        List<Post> remain = new ArrayList<>();

        for(Post post : recentList){
            String redisKey = post.getPostId().toString();
            if(!redisTemplate.hasKey(redisKey)){
                log.debug("redisKey = {}",redisKey);
                ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
                // 10분간 보여줬던 게시글은 보여주지 않는다.
                valueOperations.set(redisKey,"recommended", Duration.ofSeconds(600));
                result.add(PostFeedDTO.from(post,userId));
                if(result.size() == 10){
                    break;
                }
            }
            else{
                remain.add(post);
            }
        }
        return result;
    }
}
