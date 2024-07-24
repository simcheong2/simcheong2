package com.example.simcheong2.domain.user.controller.response;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchResponse {
    @NonNull
    private String nickname;

    @NonNull
    private String profileUrl;
}
