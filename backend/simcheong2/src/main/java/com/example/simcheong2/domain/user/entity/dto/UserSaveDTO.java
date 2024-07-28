package com.example.simcheong2.domain.user.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveDTO {
    private String inputId;
    private String password;
    private String email;
    private String name;
    private String nickname;
    private Date birthday;
    private String phone;
    private Boolean isForeign;
    private Boolean isDisabled;
    private Sex sex;

}
