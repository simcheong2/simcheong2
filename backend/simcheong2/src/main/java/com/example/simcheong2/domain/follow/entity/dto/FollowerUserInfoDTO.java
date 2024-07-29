package com.example.simcheong2.domain.follow.entity.dto;

import com.example.simcheong2.domain.follow.controller.response.FollowerUserInfoResponse;
import com.example.simcheong2.domain.user.entity.User;
import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@NoArgsConstructor
public class FollowerUserInfoDTO {
    private String nickname;
    private String profileUrl;
    private Boolean isDisabled;
    private Boolean isFollow;

    public FollowerUserInfoResponse toResponse() {
        return FollowerUserInfoResponse.builder()
                .nickname(nickname)
                .profileUrl(profileUrl)
                .isDisabled(isDisabled)
                .isFollow(isFollow)
                .build();
    }

    public static FollowerUserInfoDTO from(User me, User myFollower) {
        return FollowerUserInfoDTO.builder()
                .nickname(myFollower.getNickname())
                .profileUrl(myFollower.getProfileImage())
                .isDisabled(myFollower.getDisabled())
                .isFollow(me.isFollow(myFollower)) // 내가 내 팔로워를 팔로우 하고 있는지.
                .build();
    }

}
