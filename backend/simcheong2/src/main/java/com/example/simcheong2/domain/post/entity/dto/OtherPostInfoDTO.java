package com.example.simcheong2.domain.post.entity.dto;

import com.example.simcheong2.domain.image.controller.response.ImagesResponse;
import com.example.simcheong2.domain.image.entity.dto.ImageDTO;
import com.example.simcheong2.domain.post.controller.response.OtherPostInfoResponse;
import com.example.simcheong2.domain.post.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class OtherPostInfoDTO implements Comparator<OtherPostInfoDTO> {
    private int postId;
    private List<ImageDTO> images;
    private String content;
    private Integer likeCount;
    private Integer commentCount;
    private Boolean isLiked;
    private Boolean isReported;
    private LocalDateTime createdDate;

    public static OtherPostInfoDTO from(Post post) {
        return OtherPostInfoDTO.builder()
                .images(post.getPostImages().stream()
                        .map(ImageDTO::from)
                        .sorted() // 인덱스 순 정렬
                        .collect(Collectors.toList()))
                .content(post.getContent())
                .likeCount(post.getPostUserPostLikes().size())
                .commentCount(post.getPostComments().size())
                .isLiked(post.isSelfLiked()) // 내가 내 게시글에 좋아요 눌렀는지
                .isReported(post.isReported())
                .createdDate(post.getCreatedDate())
                .postId(post.getPostId())
                .build();
    }

    public OtherPostInfoResponse toResponse() {
        return OtherPostInfoResponse.builder()
                .images(images.stream()
                        .map(ImageDTO::toResponse)
                        .collect(Collectors.toList()))
                .content(content)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .isLiked(isLiked)
                .isReported(isReported)
                .createdAt(createdDate)
                .postId(postId)
                .build();
    }

    @Override
    public int compare(OtherPostInfoDTO o1, OtherPostInfoDTO o2) {
        return o1.createdDate.compareTo(o2.createdDate);
    }
}
