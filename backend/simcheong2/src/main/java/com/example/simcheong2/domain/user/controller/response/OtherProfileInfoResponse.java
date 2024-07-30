package com.example.simcheong2.domain.user.controller.response;
import com.example.simcheong2.domain.user.entity.dto.Sex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OtherProfileInfoResponse {
    private String profileUrl;

    private String nickname;

    private Boolean isReported;

    @Schema(description = "내가 이 사람을 팔로우 하고 있는지 여부.")
    private Boolean isFollow;

    private Sex sex; // MALE, FEMALE 스트링이 입력으로 들어오면 자동으로 매칭 됨.

    private Boolean isDisabled;

    private Integer followingCount;

    private Integer followerCount;

    @Email
    private String email;
}
