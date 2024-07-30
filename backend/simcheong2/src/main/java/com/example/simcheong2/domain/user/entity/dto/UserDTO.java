package com.example.simcheong2.domain.user.entity.dto;

import com.example.simcheong2.domain.user.entity.User;
import lombok.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED) // 빌더 패턴으로만 DTO를 만들 수 있도록 강제함. 동일 타입이라서 휴먼 에러 가능성 있음
@NoArgsConstructor
public class UserDTO {
    private int userId;
    private String name;
    private String phone;
    private String nickname;

    public static UserDTO from(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .phone(user.getPhone())
                .nickname(user.getNickname())
                .build();
    }
}
