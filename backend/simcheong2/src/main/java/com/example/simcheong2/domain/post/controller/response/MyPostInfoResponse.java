package com.example.simcheong2.domain.post.controller.response;

import com.example.simcheong2.domain.image.controller.response.ImagesResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MyPostInfoResponse {
    @NonNull
    private List<ImagesResponse> images;

    @NonNull
    private String content;

    @NonNull
    private Integer likeCount;

    @NonNull
    private Integer commentCount;

    @NonNull
    private Boolean isLiked;

    @NonNull
    private LocalDateTime createdDate;
}