package com.example.simcheong2.domain.follow.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtherFollowUserInfoResponse {
    private String nickname;

    private String profileUrl;

    private Boolean isDisabled;

    @Schema(description = "내가 이 사람을 팔로우 하고 있는지 여부.")
    private Boolean isFollow;
}