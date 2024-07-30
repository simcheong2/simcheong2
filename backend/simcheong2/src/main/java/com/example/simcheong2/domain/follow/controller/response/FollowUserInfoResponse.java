package com.example.simcheong2.domain.follow.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowUserInfoResponse {
    private String nickname;

    private String profileUrl;

    private Boolean isDisabled;
}
