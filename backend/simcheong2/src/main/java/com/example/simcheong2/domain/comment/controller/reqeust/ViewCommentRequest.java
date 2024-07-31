package com.example.simcheong2.domain.comment.controller.reqeust;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewCommentRequest {
    @NonNull
    private Long postId;
}
