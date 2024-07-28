package com.example.simcheong2.domain.auth.entity.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    private String inputId;

    private String inputPassword;
}
