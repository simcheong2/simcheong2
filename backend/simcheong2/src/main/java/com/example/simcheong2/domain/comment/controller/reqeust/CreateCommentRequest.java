package com.example.simcheong2.domain.comment.controller.reqeust;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequest {
    @NonNull
    private Long postId;

    @NonNull
    @Size(min = 1, max = 200, message = "댓글 길이는 1글자 이상 200글자 이하여야 합니다.")
    private String content;
}
