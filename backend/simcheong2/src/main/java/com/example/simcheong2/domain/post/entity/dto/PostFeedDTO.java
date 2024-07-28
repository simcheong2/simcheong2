package com.example.simcheong2.domain.post.entity.dto;

import com.example.simcheong2.domain.comment.entity.dto.CommentDTO;
import com.example.simcheong2.domain.image.entity.dto.ImageDTO;
import com.example.simcheong2.domain.post.controller.response.FeedResponse;
import com.example.simcheong2.domain.post.controller.response.OtherPostInfoResponse;
import com.example.simcheong2.domain.post.entity.Post;
import com.example.simcheong2.domain.user.controller.response.OtherUserInfoResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class PostFeedDTO {
    private String profileUrl; // fetch
    private String nickname; // fetch
    private Boolean isUserReported; //
    private Boolean isFollow; // ? 일단 post.user.following가져와볼게

    private List<ImageDTO> images; // fetch
    private String content; // 기본
    private Integer likeCount; // fetch
    private Integer commentCount; // fetch
    private Boolean isLiked; // fetch로 UserPostLike 한거에서 딸려올거임. Ser<User> 정보 조회하면 됨.
    private Boolean isPostReported; // fetch로 PostBlame 한거에서 딸려올거임. blamer에 해당 우저 id있나보면 될듯
    private LocalDateTime createdAt;

    private List<CommentDTO> comments; // fetch로

    public FeedResponse toResponse() {
        return FeedResponse.builder()
                .otherUserInfoResponse(OtherUserInfoResponse.builder()
                        .profileUrl(profileUrl)
                        .nickname(nickname)
                        .isReported(isUserReported)
                        .isFollow(isFollow)
                        .build())
                .posts(OtherPostInfoResponse.builder()
                        .images(images.stream()
                                .map(ImageDTO::toResponse)
                                .collect(Collectors.toList()))
                        .content(content)
                        .likeCount(likeCount)
                        .commentCount(commentCount)
                        .isLiked(isLiked)
                        .isReported(isPostReported)
                        .createdAt(createdAt)
                        .build())
                .comments(comments.stream()
                        .map(CommentDTO::toResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    public static PostFeedDTO from(Post post, int userId) {
        boolean userReported = post.getUser().getBlamedUserUserBlames().stream().anyMatch(
                userBlame -> userBlame.getBlamer().getUserId() == userId
        );

        boolean followed = post.getUser().getFollowerFollows().stream().anyMatch(
                follow -> follow.getFollowing().getUserId() == userId
        );

        boolean liked = post.getPostUserPostLikes().stream().anyMatch(
                userPostLike -> userPostLike.getUser().getUserId() == userId
        );

        boolean postReported = post.getBlamedPostPostBlames().stream().anyMatch(
                postBlame -> postBlame.getBlamer().getUserId() == userId
        );

        return PostFeedDTO.builder()
                .profileUrl(post.getUser().getProfileImage())
                .nickname(post.getUser().getNickname())
                .isUserReported(userReported) //
                .isFollow(followed) //
                .images(post.getPostImages().stream()
                        .map(ImageDTO::from)
                        .sorted()
                        .collect(Collectors.toList()))
                .content(post.getContent())
                .likeCount(post.getPostUserPostLikes().size())
                .commentCount(post.getPostComments().size())
                .isLiked(liked)//
                .isPostReported(postReported)
                .createdAt(post.getCreatedDate())
                .comments(post.getPostComments().stream()
                        .map(CommentDTO::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
