package com.example.simcheong2.domain.image.controller.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ImagesResponse {
    private String imageUrl;

    private String imageText;
}