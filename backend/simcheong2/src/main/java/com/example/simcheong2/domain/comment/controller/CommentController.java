package com.example.simcheong2.domain.comment.controller;

import com.example.simcheong2.domain.comment.controller.reqeust.CreateCommentRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "댓글 등록 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    @PostMapping
    public ResponseEntity<Boolean> createComment(@RequestBody @Valid CreateCommentRequest request){
        return ResponseEntity.ok(true);
    }
}
