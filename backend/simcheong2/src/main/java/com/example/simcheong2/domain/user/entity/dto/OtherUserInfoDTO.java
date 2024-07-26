package com.example.simcheong2.domain.user.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class OtherUserInfoDTO {
    private String profileUrl;
    private String nickname;
    private Boolean isReported;
    private Boolean isFollow;
}
