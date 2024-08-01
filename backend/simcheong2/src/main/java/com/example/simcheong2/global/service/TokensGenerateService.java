package com.example.simcheong2.global.service;

import com.example.simcheong2.domain.auth.entity.Tokens;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokensGenerateService {
    private static final String BEARER_TYPE = "Bearer";

    @Value("${jwt.token.time}")
    private long ACCESS_TOKEN_EXPIRE_TIME;

    @Value("${jwt.token.refresh-time}")
    private long REFRESH_TOKEN_EXPIRE_TIME;
    private static final String ACCESS_TOKEN_KIND = "accessToken";
    private static final String REFRESH_TOKEN_KIND = "refreshToken";
    private final JwtTokenService jwtTokenService;

    public Tokens generate(Integer memberID, String inputId) {
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME * 1000);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME * 1000);

        String id = memberID.toString();
        String accessToken = jwtTokenService.generate(inputId, ACCESS_TOKEN_KIND, accessTokenExpiredAt);
        String refreshToken = jwtTokenService.generate(id, REFRESH_TOKEN_KIND, refreshTokenExpiredAt);

        return Tokens.of(accessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public Tokens generateAccessToken(String refreshToken, String inputId){
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME * 1000);

        String accessToken = jwtTokenService.generate(inputId, ACCESS_TOKEN_KIND, accessTokenExpiredAt);
        return Tokens.of(accessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String extractMemberId(String accessToken) {
        return String.valueOf(jwtTokenService.extractSubject(accessToken));
    }
}
