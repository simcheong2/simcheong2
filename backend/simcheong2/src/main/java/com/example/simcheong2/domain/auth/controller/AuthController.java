package com.example.simcheong2.domain.auth.controller;

import com.example.simcheong2.domain.auth.controller.request.*;
import com.example.simcheong2.domain.auth.controller.response.SmsCheckResponse;
import com.example.simcheong2.domain.auth.controller.response.TokenResponse;
import com.example.simcheong2.domain.auth.entity.Tokens;
import com.example.simcheong2.domain.auth.entity.dto.LoginDto;
import com.example.simcheong2.domain.auth.entity.dto.LogoutDto;
import com.example.simcheong2.domain.auth.entity.dto.ReissueDto;
import com.example.simcheong2.domain.auth.service.AuthService;
import com.example.simcheong2.domain.user.entity.dto.UserSaveDTO;
import com.example.simcheong2.domain.user.service.UserCreateService;
import com.example.simcheong2.global.service.TokensGenerateService;
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
    private final UserCreateService userCreateService;
    private final TokensGenerateService tokensGenerateService;

    @PostMapping("/login")
    //로그인 되면 레디스에 토큰이 저장되고, 포스트맨 리턴으로 토큰이 올거임.
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginDto loginDto = new LoginDto(request.getId(),request.getPassword());

        //로그인 성공 했다면 토큰 발급
        Tokens tokens = authService.login(loginDto);
        log.debug("userId = {}",tokensGenerateService.extractMemberId(tokens.getAccessToken()));
        return ResponseEntity.ok(new TokenResponse(tokens.getAccessToken(),tokens.getRefreshToken()));
    }
    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestBody @Valid LogoutRequest request) {
        String accessToken = request.getAccessToken();
        LogoutDto logoutDto =new LogoutDto(accessToken);
        authService.logout(logoutDto);
        return ResponseEntity.ok(true);
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
        UserSaveDTO userSaveDTO = new UserSaveDTO(
                request.getId(),
                request.getPassword(),
                request.getEmail(),
                request.getName(),
                request.getNickname(),
                request.getOpeningDate(),
                request.getPhone(),
                request.getIsForeign(),
                request.getIsDisabled(),
                request.getSex(),
                request.getSessionId()
        );
        userCreateService.signUp(userSaveDTO);
        return ResponseEntity.ok(true);
    }
    // 재발급
    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@RequestBody @Valid ReissueRequest request) {
        ReissueDto reissueDto = new ReissueDto(request.getRefreshToken());
        authService.reissue(reissueDto);
        return ResponseEntity.ok(new TokenResponse("", ""));
    }

}
