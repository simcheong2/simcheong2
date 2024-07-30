package com.example.simcheong2.domain.comment.controller;

import com.example.simcheong2.domain.comment.controller.reqeust.CreateCommentRequest;
import com.example.simcheong2.domain.comment.controller.reqeust.ViewCommentRequest;
import com.example.simcheong2.domain.comment.controller.response.CommentResponse;
import com.example.simcheong2.domain.comment.entity.dto.CommentCreateDTO;
import com.example.simcheong2.domain.comment.entity.dto.CommentDTO;
import com.example.simcheong2.domain.comment.service.CommentCreateService;
import com.example.simcheong2.domain.comment.service.CommentSearchService;
import com.example.simcheong2.domain.post.controller.response.FeedResponse;
import com.example.simcheong2.domain.post.entity.dto.PostFeedDTO;
import com.example.simcheong2.global.service.SecurityUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "댓글 등록 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentCreateService commentCreateService;
    private final CommentSearchService commentSearchService;
    @PostMapping
    public ResponseEntity<Boolean> createComment(@RequestBody @Valid CreateCommentRequest request){
        int userId = SecurityUtil.getCurrentUserId();
        CommentCreateDTO commentCreateDTO = new CommentCreateDTO(request.getPostId(),request.getContent());
        commentCreateService.createComment(commentCreateDTO, userId);
        return ResponseEntity.ok(true);
    }
    @PostMapping("/view")
    public ResponseEntity<List<CommentResponse>> viewComment(@RequestBody @Valid ViewCommentRequest request){
        List<CommentResponse> comments = commentSearchService.getComments(request.getPostId()).stream()
                .map(CommentDTO::toResponse)
                .toList();
        return ResponseEntity.ok(comments);
    }
}
