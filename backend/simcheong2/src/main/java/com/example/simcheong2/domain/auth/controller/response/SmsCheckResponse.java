package com.example.simcheong2.domain.auth.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsCheckResponse {
    private String sessionId;
}
