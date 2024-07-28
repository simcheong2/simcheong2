package com.example.simcheong2.domain.user.controller.response;

import com.example.simcheong2.domain.user.entity.dto.Sex;
import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MyProfileInfoResponse {
    @NonNull
    private Integer followingCount;

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