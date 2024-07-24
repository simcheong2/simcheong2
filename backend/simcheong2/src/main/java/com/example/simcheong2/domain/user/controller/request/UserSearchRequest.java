package com.example.simcheong2.domain.user.controller.request;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchRequest {
    @NonNull
    @Size(min = 1, max = 20, message = "닉네임 글자 길이는 1글자 이상 20글자 이하여야 합니다.")
    private String nickname;
}
