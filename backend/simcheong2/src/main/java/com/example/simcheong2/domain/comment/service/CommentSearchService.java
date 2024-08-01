package com.example.simcheong2.domain.comment.service;
import com.example.simcheong2.domain.comment.entity.dto.CommentDTO;
import com.example.simcheong2.domain.comment.repository.CommentRepository;
import com.example.simcheong2.domain.post.entity.dto.PostFeedDTO;
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
public class CommentSearchService {
    private final CommentRepository commentRepository;
    public List<CommentDTO> getComments(Long requestId) {
        int postId = Math.toIntExact(requestId);

        return commentRepository.searchCommentByPostId(postId)
                .orElseGet(ArrayList::new)
                .stream()
                .map(CommentDTO::from)
                .collect(Collectors.toList());
    }
}
