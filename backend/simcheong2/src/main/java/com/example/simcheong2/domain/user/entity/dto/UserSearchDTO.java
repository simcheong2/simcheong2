package com.example.simcheong2.domain.user.entity.dto;

import com.example.simcheong2.domain.user.controller.response.UserSearchResponse;
import com.example.simcheong2.domain.user.entity.User;
import lombok.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED) // 빌더 패턴으로만 DTO를 만들 수 있도록 강제함. 동일 타입이라서 휴먼 에러 가능성 있음
@NoArgsConstructor
public class UserSearchDTO {
    private String nickname;
    private String profileUrl;

    public static UserSearchDTO from(User user) {
        return UserSearchDTO.builder()
                .nickname(user.getNickname())
                .profileUrl(user.getProfileImage())
                .build();
    }

    public UserSearchResponse toResponse() {
        return UserSearchResponse.builder()
                .nickname(nickname)
                .profileUrl(profileUrl)
                .build();
    }

}
