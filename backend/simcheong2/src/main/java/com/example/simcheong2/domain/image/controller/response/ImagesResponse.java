package com.example.simcheong2.domain.image.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagesResponse {
    @NonNull
    private String imageUrl;

    @NonNull
    private String imageText;
}