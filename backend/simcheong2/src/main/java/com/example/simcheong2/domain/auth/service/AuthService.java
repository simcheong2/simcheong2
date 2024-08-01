package com.example.simcheong2.domain.auth.service;

import com.example.simcheong2.domain.auth.entity.Tokens;
import com.example.simcheong2.domain.auth.entity.dto.LoginDto;
import com.example.simcheong2.domain.auth.entity.dto.LogoutDto;
import com.example.simcheong2.domain.auth.entity.dto.ReissueDto;
import com.example.simcheong2.domain.user.entity.User;
import com.example.simcheong2.domain.user.entity.dto.UserDTO;
import com.example.simcheong2.domain.user.repository.UserRepository;
import com.example.simcheong2.domain.user.service.UserValidationService;
import com.example.simcheong2.global.exception.model.CustomException;
import com.example.simcheong2.global.exception.model.ErrorCode;
import com.example.simcheong2.global.redis.service.RedisUtilService;
import com.example.simcheong2.global.service.JwtTokenService;
import com.example.simcheong2.global.service.TokensGenerateService;
import com.example.simcheong2.global.sms.SmsUtil;
import com.example.simcheong2.global.sms.SmsValidationCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserValidationService userValidationService;
    private final TokensGenerateService tokensGenerateService;
    private final RedisUtilService redisUtilService;
    private final JwtTokenService jwtTokenService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final SmsValidationCodeGenerator codeGenerator;
    private final SmsUtil smsUtil;

    private final RedisTemplate<String, String> smsRedisTemplate;

    private final UserRepository userRepository;

    public String validateSmsCode(String phone, String targetCode) {
        // 이미 등록된 유저 번호는 아닌지 확인
        checkExistUser(phone);
        // 레디스에 등록된 번호가 맞는지 일단 확인
        String code = Optional.ofNullable(smsRedisTemplate.opsForValue().get(phone))
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST, "해당 휴대폰 번호로 보낸 인증번호가 없습니다. 인증 요청 먼저 해주세요."));
        if (!code.equals(targetCode.trim())) throw new CustomException(ErrorCode.BAD_REQUEST, "인증번호가 일치하지 않습니다.");
        smsRedisTemplate.delete(phone); // 레디스에서 제거
        String sessionId = codeGenerator.generatorCode();
        log.info("세션 id는 {}, 폰 번호는 {}", sessionId, phone);
        smsRedisTemplate.opsForValue().set(sessionId, phone, 3, TimeUnit.HOURS); // 인메모리 절약을 위해.. 3시간 뒤에는 제거
        return sessionId;
    }

    public Tokens login(LoginDto loginDto) {
        Optional<User> isExist = userRepository.findByInputId(loginDto.getInputId());
        if (isExist.isEmpty()) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "아이디를 찾을 수 없습니다");
        }
        User user = isExist.get();
        String userPassword = user.getPassword();
        if (!passwordEncoder.matches(loginDto.getInputPassword(), userPassword)) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "비밀번호가 틀렸습니다");
        }
        Tokens tokens = tokensGenerateService.generate(user.getUserId(), user.getInputId());
        redisUtilService.setRefreshToken(user.getUserId().toString(), tokens.getRefreshToken());

        return tokens;
    }

    public void logout(LogoutDto logoutDto) {
        String accessToken = logoutDto.getAccessToken();

        String userInputId = tokensGenerateService.extractMemberId(accessToken);
        Optional<User> isExist = userRepository.findByInputId(userInputId);
        if (isExist.isEmpty()) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "유저를 찾을 수 없는 logout 요청입니다.");
        }
        User user = isExist.get();
        String userId = user.getUserId().toString();

        redisUtilService.deleteData(userId);
    }

    public void createCode(String phone) {
        checkExistUser(phone);
        isAlreadySendCode(phone);
        sendCode(phone);
    }

    private void checkExistUser(String phone) {
        Optional<UserDTO> user = userValidationService.isPhoneNumberAlreadyRegistered(phone.replace("-", ""));
        if (user.isPresent()) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "이미 가입된 전화번호입니다.");
        }
    }

    private void isAlreadySendCode(String phone) {
        // 레디스에 남아있는 번호는 아닌지 확인.
        if (smsRedisTemplate.opsForValue().get(phone) != null) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "이미 인증번호를 발송했습니다. 5분간 유효합니다.");
        }
    }

    private void sendCode(String phone) {
        String code = codeGenerator.generatorCode();
        smsUtil.sendOne(phone, code);
        smsRedisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
    }

    public Tokens reissue(ReissueDto reissueDto) {
        String userId = jwtTokenService.extractSubject(reissueDto.getRefreshToken());
        String redisRefreshToken = redisUtilService.getData(userId);
        if(redisRefreshToken == null){
            throw new CustomException(ErrorCode.NOT_HAVE_AUTHORIZATION, "인증 만료");
        }
        if(redisRefreshToken.equals(reissueDto.getRefreshToken())){
            Optional<User> isExist = userRepository.findByUserId(Integer.parseInt(userId));
            if(isExist.isEmpty()) {
                throw new CustomException(ErrorCode.BAD_REQUEST, "refreshToken 으로 유저를 찾을 수 없음.");
            }
            User user = isExist.get();
            return tokensGenerateService.generateAccessToken(redisRefreshToken, user.getInputId());
        }
        else{
            throw new CustomException(ErrorCode.BAD_REQUEST,"요청과 redis 의 refreshToken 이 일치하지 않습니다.");
        }
    }
}
