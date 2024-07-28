package com.example.simcheong2.domain.image.controller.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ImagesResponse {
    @NonNull
    private String imageUrl;

    @NonNull
    private String imageText;
}