package com.example.simcheong2.domain.comment.controller.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CommentResponse {
    @NonNull
    private String nickname;

    @NonNull
    private String comment;
}