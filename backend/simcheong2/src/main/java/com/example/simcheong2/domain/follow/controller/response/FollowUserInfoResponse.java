package com.example.simcheong2.domain.follow.controller.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class FollowUserInfoResponse {
    private String nickname;

    private String profileUrl;

    private Boolean isDisabled;
}
