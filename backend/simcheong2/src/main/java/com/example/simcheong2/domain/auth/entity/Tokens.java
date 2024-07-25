package com.example.simcheong2.domain.auth.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tokens {
    private String accessToken;
    private String refreshToken;
    private String grantType;
    private Long expiresIn;

    public static Tokens of(String accessToken, String refreshToken, String grantType, Long expiresIn) {
        return new Tokens(accessToken, refreshToken, grantType, expiresIn);
    }
}
