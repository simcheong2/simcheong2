package com.example.simcheong2.domain.user_post_like.service;

import com.example.simcheong2.domain.post.entity.Post;
import com.example.simcheong2.domain.post.repository.PostRepository;
import com.example.simcheong2.domain.user.entity.User;
import com.example.simcheong2.domain.user.repository.UserRepository;
import com.example.simcheong2.domain.user_post_like.entity.UserPostLike;
import com.example.simcheong2.domain.user_post_like.repository.UserPostLikeRepository;
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
public class LikeService {
    private final UserPostLikeRepository userPostLikeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void likePost(int userId, int postId) {
        Optional<UserPostLike> userPostLike = userPostLikeRepository.findByUserUserIdAndPostPostId(userId, postId);
        if (userPostLike.isPresent()) {
            log.info("유저 {}가 게시글 {}에 좋아요 취소", userId, postId);
            userPostLikeRepository.delete(userPostLike.get());
        } else {
            addUserPostLike(userId, postId);
        }
    }

    private void addUserPostLike(int userId, int postId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST, "등록된 유저가 아닙니다"));

        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST, "등록된 게시물이 아닙니다"));
        checkUserPossiblePostLike(user, post);

        log.info("유저 {}가 게시글 {}에 좋아요", userId, postId);
        userPostLikeRepository.save(UserPostLike.builder()
                .user(user)
                .post(post)
                .build());
    }

    private void checkUserPossiblePostLike(User user, Post post) {
        User postOwner = post.getUser();
        if (!postOwner.isPossibleLike(user)) {
            log.info("유저 {}가 게시글 {}에 좋아요할 수 없어요", user.getUserId(), postOwner.getUserId());
            throw new CustomException(ErrorCode.BAD_REQUEST, "숨김 계정이므로 해당 유저가 팔로우하는 유저들만 좋아요를 누를 수 있습니다.");
        }
    }

}
