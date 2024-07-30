package com.example.simcheong2.domain.user.entity.dto;

import com.example.simcheong2.domain.post.entity.dto.MyPostInfoDTO;
import com.example.simcheong2.domain.user.controller.response.MyPageResponse;
import com.example.simcheong2.domain.user.controller.response.MyProfileInfoResponse;
import com.example.simcheong2.domain.user.entity.User;
import lombok.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class MyPageDTO {
    private Integer followingCount; // 내가 팔로우하는 사람들
    private Integer followerCount; // 나를 팔로우한 사람들
    private String email;
    private String profileUrl;
    private String nickname;
    private Sex sex; // MALE, FEMALE 스트링이 입력으로 들어오면 자동으로 매칭 됨.
    private Boolean isDisabled;

    private List<MyPostInfoDTO> posts;

    public static MyPageDTO from(User user) {
        return MyPageDTO.builder()
                .followingCount(user.getFollowingFollows().size())
                .followerCount(user.getFollowerFollows().size())
                .email(user.getEmail())
                .profileUrl(user.getProfileImage())
                .nickname(user.getNickname())
                .sex(user.getSex())
                .isDisabled(user.getDisabled())
                .posts(user.getUserPosts().stream()
                        .map(MyPostInfoDTO::from)
                        .collect(Collectors.toList()))
                .build();
    }

    public MyPageResponse toResponse() {
        return MyPageResponse.builder()
                .posts(posts.stream()
                        .sorted(Comparator.comparing(MyPostInfoDTO::getCreatedDate).reversed())
                        .map(MyPostInfoDTO::toResponse)
                        .collect(Collectors.toList()))
                .profile(MyProfileInfoResponse.builder()
                        .followingCount(followingCount)
                        .followerCount(followerCount)
                        .email(email)
                        .profileUrl(profileUrl)
                        .nickname(nickname)
                        .sex(sex)
                        .isDisabled(isDisabled)
                        .build())
                .build();
    }
}
