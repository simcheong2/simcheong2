package com.example.simcheong2.domain.comment.entity.dto;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class CommentCreateDTO {
    private Long postId;
    private String comment;
}
