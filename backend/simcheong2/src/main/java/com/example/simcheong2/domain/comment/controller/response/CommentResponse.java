package com.example.simcheong2.domain.comment.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    @NonNull
    private String nickname;

    @NonNull
    private String comment;
}