package com.example.simcheong2.domain.post.controller.response;

import com.example.simcheong2.domain.image.controller.response.ImagesResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OtherPostInfoResponse {
    private int postId;

    private List<ImagesResponse> images;

    private String content;

    private Integer likeCount;

    private Integer commentCount;

    private Boolean isLiked;

    private Boolean isReported;

    private LocalDateTime createdAt;
}