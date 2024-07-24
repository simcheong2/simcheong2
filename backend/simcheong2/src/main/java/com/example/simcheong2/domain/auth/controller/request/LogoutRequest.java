package com.example.simcheong2.domain.auth.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogoutRequest {
    @NonNull
    private String accessToken;

    @NonNull
    private String refreshToken;
}
