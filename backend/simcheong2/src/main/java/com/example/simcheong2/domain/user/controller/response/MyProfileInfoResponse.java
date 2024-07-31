package com.example.simcheong2.domain.user.controller.response;

import com.example.simcheong2.domain.user.entity.dto.Sex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MyProfileInfoResponse {
    @Schema(description = "내가 팔로우하는 사람들의 숫자")
    private Integer followingCount;
    @Schema(description = "나를 팔로우하는 사람들의 숫자. 내가 얼마나 인기인인지 지표")
    private Integer followerCount;

    @Email
    private String email;

    private String profileUrl;

    private String nickname;

    private Sex sex; // MALE, FEMALE 스트링이 입력으로 들어오면 자동으로 매칭 됨.

    private Boolean isDisabled;
}