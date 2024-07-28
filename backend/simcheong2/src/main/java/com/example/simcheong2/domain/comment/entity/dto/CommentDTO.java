package com.example.simcheong2.domain.comment.entity.dto;

import com.example.simcheong2.domain.comment.controller.response.CommentResponse;
import com.example.simcheong2.domain.comment.entity.Comment;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class CommentDTO {
    private String nickname;
    private String comment;

    public CommentResponse toResponse() {
        return CommentResponse.builder()
                .nickname(nickname)
                .comment(comment)
                .build();
    }

    public static CommentDTO from(Comment comment) {
        return CommentDTO.builder()
                .nickname(comment.getUser().getNickname())
                .comment(comment.getContent())
                .build();
    }
}
