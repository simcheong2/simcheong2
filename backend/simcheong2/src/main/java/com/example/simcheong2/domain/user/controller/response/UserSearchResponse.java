package com.example.simcheong2.domain.user.controller.response;

import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserSearchResponse {
    private String nickname;

    private String profileUrl;
}
