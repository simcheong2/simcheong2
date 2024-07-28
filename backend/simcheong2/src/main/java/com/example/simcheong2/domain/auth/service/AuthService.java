package com.example.simcheong2.domain.auth.service;

import com.example.simcheong2.domain.auth.entity.Tokens;
import com.example.simcheong2.domain.auth.entity.dto.LoginDto;
import com.example.simcheong2.domain.auth.entity.dto.LogoutDto;
import com.example.simcheong2.domain.auth.entity.dto.ReissueDto;
import com.example.simcheong2.domain.user.entity.User;
import com.example.simcheong2.domain.user.entity.dto.UserDTO;
import com.example.simcheong2.domain.user.repository.UserRepository;
import com.example.simcheong2.domain.user.service.UserCreateService;
import com.example.simcheong2.domain.user.service.UserDeleteService;
import com.example.simcheong2.domain.user.service.UserValidationService;
import com.example.simcheong2.global.exception.model.CustomException;
import com.example.simcheong2.global.exception.model.ErrorCode;
import com.example.simcheong2.global.security.redis.repository.RedisTokensRepository;
import com.example.simcheong2.global.security.redis.service.RedisUtilService;
import com.example.simcheong2.global.service.JwtTokenService;
import com.example.simcheong2.global.service.TokensGenerateService;
import com.example.simcheong2.global.sms.SmsUtil;
import com.example.simcheong2.global.sms.SmsValidationCodeGenerator;
import jakarta.servlet.http.HttpServletRequest;
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
    private final UserCreateService userCreateService;
    private final UserDeleteService userDeleteService;
    private final TokensGenerateService tokensGenerateService;
    private final RedisUtilService redisUtilService;
    private final RedisTokensRepository redisTokensRepository;
    private final JwtTokenService jwtTokenService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final SmsValidationCodeGenerator codeGenerator;
    private final SmsUtil smsUtil;

    private final RedisTemplate<String, String> smsRedisTemplate;

    private final UserRepository userRepository;

    @Transactional
    public Tokens login(LoginDto loginDto){
        Optional<User> isExist =  userRepository.findByInputId(loginDto.getInputId());
        if(isExist.isEmpty()){
            throw new CustomException(ErrorCode.BAD_REQUEST, "아이디를 찾을 수 없습니다");
        }
        User user = isExist.get();
        String userPassword =user.getPassword();
        if(!userPassword.equals(loginDto.getInputPassword())){
        //if(!passwordEncoder.matches(userPassword, loginDto.getInputPassword())){
            log.debug("userPassword:" + userPassword);
            log.debug("inputPassword:" + loginDto.getInputPassword());
            throw new CustomException(ErrorCode.BAD_REQUEST, "비밀번호가 틀렸습니다");
        }
        Tokens tokens = tokensGenerateService.generate(user.getUserId(), user.getInputId());
//        log.debug("access token :" + tokens.getAccessToken());
//        log.debug("refresh token :" + tokens.getRefreshToken());
        //redisUtilService.setData(user.getUserId().toString(), tokens.getRefreshToken());
        // key : UserId, value : refreshToken 으로 redis 에 50400 초 동안 저장
        // reissue 시 보안을 위해 저장을 위함이라네요/
        // 재발급용 리프레시 토큰을 레디스에 저장
        log.debug("refreshToken = " + tokens.getRefreshToken());
        redisUtilService.setRefreshToken(user.getUserId().toString(), tokens.getRefreshToken());
        // key : inputId, value : "login" 으로 redis 에 7200 초 동안 저장
        // 중복 로그인을 방지 하기 위함이라네요.
        // 이건 없어도 될 것 같음. 중복 로그인을 방지하기 위해 accessToken 을 레디스에 저장한것이긴 함.
        redisUtilService.setAccessToken(user.getInputId(), "login");

        return tokens;
    }
    @Transactional
    public void logout(LogoutDto logoutDto){
        String accessToken = logoutDto.getAccessToken();
        //
        log.debug("userId = {}",tokensGenerateService.extractMemberId(accessToken));
        String userInputId = tokensGenerateService.extractMemberId(accessToken);
        Optional<User> isExist = userRepository.findByInputId(userInputId);
        if(isExist.isEmpty()){
            throw new CustomException(ErrorCode.BAD_REQUEST,"유저를 찾을 수 없는 logout 요청입니다.");
        }
        User user = isExist.get();
        String userId = user.getUserId().toString();

        // 리프레쉬 토큰 삭제.
        redisUtilService.deleteData(userId);
        // 로그인된 액세스토큰 삭제.
        redisUtilService.deleteData(userInputId);
        log.debug("레디스 서버에서 토큰 삭제 완료.");
    }

    @Transactional
    public Tokens test(LoginDto loginDto){
        Integer userId = 1234;
        String inputId = "school";
        Tokens tokens = tokensGenerateService.generate(userId,inputId);
        log.debug("access token :" + tokens.getAccessToken());
        log.debug("refresh token :" + tokens.getRefreshToken());
        redisUtilService.setRefreshToken(userId.toString(), tokens.getRefreshToken());
        // key : inputId, value : "login" 으로 redis 에 7200 초 동안 저장
        // 중복 로그인을 방지 하기 위함이라네요.
        redisUtilService.setAccessToken(inputId, "login");
        return tokens;
    }

    @Transactional
    public void createCode(String phone) {
        Optional<UserDTO> user = userValidationService.isPhoneNumberAlreadyRegistered(phone);
        if (user.isPresent()) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "이미 가입된 전화번호입니다.");
        }
        // 레디스에 남아있는 번호는 아닌지 확인. -> 5분 동안 유지된다고 말하기
        if(smsRedisTemplate.opsForValue().get(phone) != null) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "이미 인증번호를 발송했습니다. 5분간 유효합니다.");
        }

        String code = codeGenerator.generatorCode();
//        smsUtil.sendOne(phone, code);
        log.info("{}에게 {}를 전송함", phone, code);
        // 레디스에 폰,번호 쌍 5분 유효시간으로 추가
        smsRedisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
    }

    @Transactional
    public void reissue(ReissueDto reissueDto){
        log.debug("refreshToken input = {}",reissueDto.getRefreshToken());
        String userId = jwtTokenService.extractSubject(reissueDto.getRefreshToken());
        String redisRefreshToken = redisUtilService.getData(userId);
        log.debug("refreshToken in redis= {}",redisRefreshToken);
        Optional<User> isExist = userRepository.findByUserId(Integer.parseInt(userId));
        if(isExist.isEmpty()){
            throw new CustomException(ErrorCode.BAD_REQUEST,"refreshToken 으로 유저를 찾을 수 없음.");
        }
        User user = isExist.get();
    }
}
