package com.example.simcheong2.domain.auth.controller;

import com.example.simcheong2.domain.auth.controller.request.*;
import com.example.simcheong2.domain.auth.controller.response.SmsCheckResponse;
import com.example.simcheong2.domain.auth.controller.response.TokenResponse;
import com.example.simcheong2.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "로그인 관련 API")
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(new TokenResponse("", ""));
    }

    // 코드 검사
    @GetMapping("/validations/sms")
    public ResponseEntity<SmsCheckResponse> checkCode(@RequestBody @Valid SmsValidationRequest request) {
        authService.validateSmsCode(request.getPhone(), request.getCode());
        return ResponseEntity.ok(new SmsCheckResponse(true));
    }

    // 코드 생성 요청
    @PostMapping("/validations/sms")
    public ResponseEntity<SmsCheckResponse> createCode(@RequestBody @Valid SmsCheckRequest request) {
        authService.createCode(request.getPhone());
        return ResponseEntity.ok(new SmsCheckResponse(true));
    }

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signup(@RequestBody @Valid SignupRequest request) {
        return ResponseEntity.ok(true);
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestBody @Valid LogoutRequest request) {
        return ResponseEntity.ok(true);
    }

    // 재발급
    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@RequestBody @Valid ReissueRequest request) {
        return ResponseEntity.ok(new TokenResponse("", ""));
    }

}
