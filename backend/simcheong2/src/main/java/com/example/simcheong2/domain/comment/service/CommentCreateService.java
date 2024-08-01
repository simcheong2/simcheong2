package com.example.simcheong2.domain.comment.service;

import com.example.simcheong2.domain.comment.entity.Comment;
import com.example.simcheong2.domain.comment.entity.dto.CommentCreateDTO;
import com.example.simcheong2.domain.comment.repository.CommentRepository;
import com.example.simcheong2.domain.post.entity.Post;
import com.example.simcheong2.domain.post.entity.dto.ImageAnalysisResultDTO;
import com.example.simcheong2.domain.post.repository.PostRepository;
import com.example.simcheong2.domain.user.entity.User;
import com.example.simcheong2.domain.user.repository.UserRepository;
import com.example.simcheong2.global.exception.model.CustomException;
import com.example.simcheong2.global.exception.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CommentCreateService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public void createComment(CommentCreateDTO commentCreateDTO, int userId) {
        int postId = Math.toIntExact(commentCreateDTO.getPostId()); // 지금 엔티티 id 타입이 인티저임. 나중에 한 번에 수정해야할듯.
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST, "등록된 게시물이 아닙니다"));
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "등록된 유저가 아닙니다"));
        createComment(user,post, commentCreateDTO.getComment());
    }

    private void createComment(User user, Post post, String content) {
        Comment newComment = new Comment((content));
        post.addComment(newComment);
        user.addComment(newComment);
    }
}
