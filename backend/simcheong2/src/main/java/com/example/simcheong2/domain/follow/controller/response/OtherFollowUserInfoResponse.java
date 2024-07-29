package com.example.simcheong2.domain.follow.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@NoArgsConstructor
public class OtherFollowUserInfoResponse {
    private String nickname;

    private String profileUrl;

    private Boolean isDisabled;

    @Schema(description = "내가 이 사람을 팔로우 하고 있는지 여부.")
    private Boolean isFollow;

    @Schema(description = "나 자신에 대한 프로필인지 여부")
    private Boolean isMine;
}