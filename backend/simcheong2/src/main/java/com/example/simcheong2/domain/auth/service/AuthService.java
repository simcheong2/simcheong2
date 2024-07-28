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

    public void validateSmsCode(String phone, String targetCode) {
        // 이미 등록된 유저 번호는 아닌지 확인
        checkExistUser(phone);
        // 레디스에 등록된 번호가 맞는지 일단 확인
        String code = Optional.ofNullable(smsRedisTemplate.opsForValue().get(phone))
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST, "해당 휴대폰 번호로 보낸 인증번호가 없습니다. 인증 요청 먼저 해주세요."));
        if (!code.equals(targetCode.trim())) throw new CustomException(ErrorCode.BAD_REQUEST, "인증번호가 일치하지 않습니다.");
        smsRedisTemplate.delete(phone); // 레디스에서 제거
    }

    public Tokens login(LoginDto loginDto){
        Optional<User> isExist =  userRepository.findByInputId(loginDto.getInputId());
        if(isExist.isEmpty()){
            throw new CustomException(ErrorCode.BAD_REQUEST, "아이디를 찾을 수 없습니다");
        }
        User user = isExist.get();
        String userPassword =user.getPassword();
        log.debug("userPassword:" + userPassword);
        log.debug("inputPassword:" + loginDto.getInputPassword());
        if(!passwordEncoder.matches(loginDto.getInputPassword(), userPassword)){
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
        // 중복 로그인을 방지 하기 위함이라네요.
        // 이건 없어도 될 것 같음. 중복 로그인을 방지하기 위해 accessToken 을 레디스에 저장한것이긴 함.
        redisUtilService.setAccessToken(user.getInputId(), "login");

        return tokens;
    }
  
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

        // acc로 찾지말고 refresh 받은거로 redis 다 지워라.
        // acc, ref 둘다 만료 -> refresh 만료 -> 401 처리를 클라

        redisUtilService.deleteData(userId);
        // 로그인된 액세스토큰 삭제.
        redisUtilService.deleteData(userInputId);
        log.debug("레디스 서버에서 토큰 삭제 완료.");
    }

    public void createCode(String phone) {
        checkExistUser(phone);
        isAlreadySendCode(phone);
        sendCode(phone);
    }

    private void checkExistUser(String phone) {
        Optional<UserDTO> user = userValidationService.isPhoneNumberAlreadyRegistered(phone);
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
        smsUtil.sendOne(phone, code); // 하면 진짜 전송됨ㅋㅋ
        log.info("{}에게 {}를 전송함", phone, code);
        smsRedisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
    }

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
