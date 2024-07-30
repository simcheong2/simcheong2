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
        // 1. post 들을 create_date 순으로 정렬하여 가져옴.
        // 2. 해당 Post 가 왜래 키로 참조하는 user 의 postVisible = true 인 post 들만 거기서 가져옴
        // 3. 레디스에, 해당 게시글의 post id 를 일정시간(10분) 저장함. 만약 2 과정에서 선택한 게시글의 post id가
        // 레디스에 이미 저장되어 있을 경우, 해당 post 대신 다른 상위 post를 가져온다.
        int userId = SecurityUtil.getCurrentUserId();
        List<Post> recentList = postRepository.findRecentPosts(userId)
                .orElseGet(ArrayList::new);
        List<PostFeedDTO> result = new ArrayList<>();
        // 내가 봤던 게시글을 새로 요청 보냈을때 보여주지 않으려면 그 게시글 id 기억해야함 (10분간)
        // random(매번) - 최신순으로 올수록 가중치 높게(랜덤성있게)
        // 새로 요청 보냈을떄 봤던게 뜰 확률도 있는거 어쩔수없나
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
        }
        return result;
    }
}
