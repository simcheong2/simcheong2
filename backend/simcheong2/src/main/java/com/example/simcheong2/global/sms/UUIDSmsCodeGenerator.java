package com.example.simcheong2.global.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Slf4j
@Component
public class UUIDSmsCodeGenerator implements SmsValidationCodeGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();


    @Override
    public String generatorCode() {
        StringBuilder stringBuilder = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            stringBuilder.append(CHARACTERS.charAt(index));
        }
        String code = stringBuilder.toString();
        log.info("생성한 인증 코드: {}", code); // 보안상 안좋으나 개발 편의성으로 넣음.
        return stringBuilder.toString();
    }
}
