package com.example.simcheong2.domain.post.entity.dto;

import com.example.simcheong2.domain.image.entity.dto.ImageDTO;
import com.example.simcheong2.domain.post.controller.response.MyPostInfoResponse;
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
public class MyPostInfoDTO implements Comparator<MyPostInfoDTO> {
    private List<ImageDTO> images;
    private String content;
    private Integer likeCount;
    private Integer commentCount;
    private Boolean isLiked;
    private LocalDateTime createdDate;

    public static MyPostInfoDTO from(Post post) {
        return MyPostInfoDTO.builder()
                .images(post.getPostImages().stream()
                        .map(ImageDTO::from)
                        .sorted() // 인덱스 순 정렬
                        .collect(Collectors.toList()))
                .content(post.getContent())
                .likeCount(post.getPostUserPostLikes().size())
                .commentCount(post.getPostComments().size())
                .isLiked(post.isSelfLiked()) // 내가 내 게시글에 좋아요 눌렀는지
                .createdDate(post.getCreatedDate())
                .build();
    }

    public MyPostInfoResponse toResponse() {
        return MyPostInfoResponse.builder()
                .images(images.stream()
                        .map(ImageDTO::toResponse)
                        .collect(Collectors.toList()))
                .content(content)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .isLiked(isLiked)
                .createdDate(createdDate)
                .build();
    }

    @Override
    public int compare(MyPostInfoDTO o1, MyPostInfoDTO o2) {
        return o1.createdDate.compareTo(o2.createdDate);
    }

}
