package com.example.simcheong2.domain.follow.entity.dto;

import com.example.simcheong2.domain.follow.controller.response.OtherFollowUserInfoResponse;
import com.example.simcheong2.domain.follow.controller.response.OtherFollowerUserInfoResponse;
import com.example.simcheong2.domain.user.entity.User;
import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@NoArgsConstructor
public class OtherFollowerUserInfoDTO {
    private String nickname;
    private String profileUrl;
    private Boolean isDisabled;
    private Boolean isFollow;
    private Boolean isMine;

    public OtherFollowerUserInfoResponse toResponse() {
        return OtherFollowerUserInfoResponse.builder()
                .nickname(nickname)
                .profileUrl(profileUrl)
                .isDisabled(isDisabled)
                .isFollow(isFollow)
                .isMine(isMine)
                .build();
    }

    public static OtherFollowerUserInfoDTO from(User me, User otherFollower) {
        return OtherFollowerUserInfoDTO.builder()
                .nickname(otherFollower.getNickname())
                .profileUrl(otherFollower.getProfileImage())
                .isDisabled(otherFollower.getDisabled())
                .isFollow(me.isFollow(otherFollower)) // 내가, 다른 사람이 팔로우하는 사람을 팔로우 하고 있는지.
                .isMine(me.getUserId() == otherFollower.getUserId())
                .build();
    }

}