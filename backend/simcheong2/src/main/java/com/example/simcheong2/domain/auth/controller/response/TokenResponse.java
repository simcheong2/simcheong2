package com.example.simcheong2.domain.auth.controller.response;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    @NonNull
    private String accessToken;

    @NonNull
    private String refreshToken;
}
