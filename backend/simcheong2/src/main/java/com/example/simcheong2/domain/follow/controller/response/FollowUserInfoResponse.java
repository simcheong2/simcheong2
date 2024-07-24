package com.example.simcheong2.domain.follow.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowUserInfoResponse {
    @NonNull
    private String nickname;

    @NonNull
    private String profileUrl;

    @NonNull
    private Boolean isDisabled;
}
