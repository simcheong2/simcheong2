package com.example.simcheong2.domain.follow.entity.dto;

import com.example.simcheong2.domain.follow.controller.response.FollowUserInfoResponse;
import com.example.simcheong2.domain.user.entity.User;
import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@NoArgsConstructor
public class FollowUserInfoDTO {
    private String nickname;
    private String profileUrl;
    private Boolean isDisabled;

    public FollowUserInfoResponse toResponse() {
        return FollowUserInfoResponse.builder()
                .nickname(nickname)
                .profileUrl(profileUrl)
                .isDisabled(isDisabled)
                .build();
    }

    public static FollowUserInfoDTO from(User user) {
        return FollowUserInfoDTO.builder()
                .nickname(user.getNickname())
                .profileUrl(user.getProfileImage())
                .isDisabled(user.getDisabled())
                .build();
    }

}
