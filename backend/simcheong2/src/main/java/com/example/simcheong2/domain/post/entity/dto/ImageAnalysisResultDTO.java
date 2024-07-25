package com.example.simcheong2.domain.post.entity.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class ImageAnalysisResultDTO {
    private String imageUrl;
    private String analyzedText;

}
