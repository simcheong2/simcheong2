package com.example.simcheong2.domain.user_post_like.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeRequest {
    @NonNull
    @Schema(description = "게시글 id")
    private Long id;
}
