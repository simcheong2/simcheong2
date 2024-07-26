package com.example.simcheong2.domain.auth.controller.request;

import com.example.simcheong2.global.valid.PhoneNumber;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsCheckRequest {
    @NonNull
    @Size(min=1, max=20, message = "이름 글자 길이는 1글자 이상 20글자 이하여야합니다.")
    private String name;

    @NonNull
    @PhoneNumber
    private String phone;
}
