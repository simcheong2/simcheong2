package com.example.simcheong2.domain.auth.controller.request;

import com.example.simcheong2.domain.user.entity.dto.Sex;
import com.example.simcheong2.global.valid.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.lettuce.core.BitFieldArgs;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.OffsetDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NonNull
    @Pattern(regexp = "^[a-z]+[a-z0-9]{5,19}$", message = "올바른 ID 형식이 아닙니다.")
    private String id;

    @NonNull
    @Pattern(regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$", message = "올바른 비밀번호 형식이 아닙니다.")
    private String password;

    @Email
    private String email;

    @NonNull
    @Size(min = 1, max = 20, message = "이름 글자 길이는 1글자 이상 20글자 이하여야합니다.")
    private String name;

    @NonNull
    private Boolean isForeign;

    @NonNull
    private Sex sex; // MALE, FEMALE 스트링이 입력으로 들어오면 자동으로 매칭 됨.

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private Date openingDate;

    @NonNull
    @PhoneNumber
    private String phone;

    @NonNull
    private Boolean isDisabled;

    @NonNull
    @Size(min = 1, max = 20, message = "닉네임 글자 길이는 1글자 이상 20글자 이하여야 합니다.")
    private String nickname;

    @NonNull
    private String sessionId;
}
