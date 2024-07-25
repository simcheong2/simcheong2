package com.example.simcheong2.domain.post.entity.dto;

import com.example.simcheong2.domain.image.controller.response.ImagesResponse;
import com.example.simcheong2.domain.image.entity.dto.ImageDTO;
import com.example.simcheong2.domain.post.controller.response.OtherPostInfoResponse;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class OtherPostInfoDTO {
    private List<ImageDTO> images;
    private String content;
    private Integer likeCount;
    private Integer commentCount;
    private Boolean isLiked;
    private Boolean isReported;

    public OtherPostInfoResponse toResponse() {
        return OtherPostInfoResponse.builder()
                .images(images.stream().map(ImageDTO::toResponse).collect(Collectors.toList()))
                .content(content)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .isLiked(isLiked)
                .isReported(isReported)
                .build();
    }
}
