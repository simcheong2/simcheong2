package com.example.simcheong2.domain.image.entity.dto;

import com.example.simcheong2.domain.image.controller.response.ImagesResponse;
import com.example.simcheong2.domain.image.entity.Image;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class ImageDTO implements Comparable {
    private String imageUrl;
    private String imageText;
    private int index;

    public ImagesResponse toResponse() {
        return ImagesResponse.builder()
                .imageText(imageText)
                .imageUrl(imageUrl)
                .build();
    }

    public static ImageDTO from(Image image) {
        return ImageDTO.builder()
                .imageUrl(image.getFileUrl())
                .imageText(image.getImageText())
                .index(image.getImageIndex())
                .build();
    }

    @Override
    public int compareTo(@NonNull Object o) {
        ImageDTO otherImage = (ImageDTO) o;
        return this.getIndex() - otherImage.getIndex();
    }
}
