package com.example.simcheong2.domain.auth.controller.request;

import com.example.simcheong2.domain.user.entity.dto.Sex;
import com.example.simcheong2.global.valid.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsValidationRequest {
    @Size(min = 1, max = 7, message = "인증 코드 길이는 1글자 이상 7글자 이하입니다.")
    private String code;
}
