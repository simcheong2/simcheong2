package com.example.simcheong2.domain.auth.controller.request;

import com.example.simcheong2.domain.user.entity.dto.Sex;
import com.example.simcheong2.global.valid.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsCheckRequest {
    @NonNull
    @Size(min=1, max=20, message = "이름 글자 길이는 1글자 이상 20글자 이하여야합니다.")
    private String name;

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private Date birthDate;

    @NonNull
    @PhoneNumber
    private String phone;
}
