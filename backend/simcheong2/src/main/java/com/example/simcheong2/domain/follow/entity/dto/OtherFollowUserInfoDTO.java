package com.example.simcheong2.domain.follow.entity.dto;

import com.example.simcheong2.domain.follow.controller.response.FollowerUserInfoResponse;
import com.example.simcheong2.domain.follow.controller.response.OtherFollowUserInfoResponse;
import com.example.simcheong2.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@NoArgsConstructor
public class OtherFollowUserInfoDTO {
    private String nickname;
    private String profileUrl;
    private Boolean isDisabled;
    private Boolean isFollow;
    private Boolean isMine;

    public OtherFollowUserInfoResponse toResponse() {
        return OtherFollowUserInfoResponse.builder()
                .nickname(nickname)
                .profileUrl(profileUrl)
                .isDisabled(isDisabled)
                .isFollow(isFollow)
                .isMine(isMine)
                .build();
    }

    public static OtherFollowUserInfoDTO from(User me, User otherFollower) {
        return OtherFollowUserInfoDTO.builder()
                .nickname(otherFollower.getNickname())
                .profileUrl(otherFollower.getProfileImage())
                .isDisabled(otherFollower.getDisabled())
                .isFollow(me.isFollow(otherFollower)) // 내가, 다른 사람이 팔로우하는 사람을 팔로우 하고 있는지.
                .isMine(me.getUserId() == otherFollower.getUserId())
                .build();
    }

}