package com.example.simcheong2.domain.user.controller.response;

import com.example.simcheong2.domain.user.entity.dto.Sex;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyProfileInfoResponse {
    @NonNull
    private Integer followCount;

    @NonNull
    private Integer followerCount;

    @Email
    private String email;

    @NonNull
    private String profileUrl;

    @NonNull
    private String nickname;

    @NonNull
    private Sex sex; // MALE, FEMALE 스트링이 입력으로 들어오면 자동으로 매칭 됨.

    @NonNull
    private Boolean isDisabled;
}