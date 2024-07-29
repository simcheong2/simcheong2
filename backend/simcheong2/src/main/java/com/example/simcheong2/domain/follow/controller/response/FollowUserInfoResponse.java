package com.example.simcheong2.domain.follow.controller.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class FollowUserInfoResponse {
    @NonNull
    private String nickname;

    @NonNull
    private String profileUrl;

    @NonNull
    private Boolean isDisabled;
}
