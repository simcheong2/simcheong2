package com.example.simcheong2.domain.auth.controller.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NonNull
    @Pattern(regexp = "^[a-z]+[a-z0-9]{5,19}$", message = "올바른 ID 형식이 아닙니다.")
    private String id;

    @NonNull
    @Pattern(regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$", message = "올바른 비밀번호 형식이 아닙니다.")
    private String password;
}
