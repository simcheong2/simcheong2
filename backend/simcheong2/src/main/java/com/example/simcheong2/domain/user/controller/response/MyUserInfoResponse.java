package com.example.simcheong2.domain.user.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUserInfoResponse {
    private String profileUrl;

    private String nickname;
}