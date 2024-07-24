package com.example.simcheong2.domain.user.controller.response;
import com.example.simcheong2.domain.user.entity.dto.Sex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtherProfileInfoResponse {
    @NonNull
    private String profileUrl;

    @NonNull
    private String nickname;

    @NonNull
    private Boolean isReported;

    @NonNull
    @Schema(description = "내가 이 사람을 팔로우 하고 있는지 여부.")
    private Boolean isFollow;

    @NonNull
    private Sex sex; // MALE, FEMALE 스트링이 입력으로 들어오면 자동으로 매칭 됨.

    @NonNull
    private Boolean isDisabled;

    @NonNull
    private Integer followCount;

    @NonNull
    private Integer followerCount;

    @Email
    private String email;
}
