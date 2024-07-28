package com.example.simcheong2.domain.user.service;

import com.example.simcheong2.domain.user.entity.User;
import com.example.simcheong2.domain.user.entity.dto.Sex;
import com.example.simcheong2.domain.user.entity.dto.UserSaveDTO;
import com.example.simcheong2.domain.user.repository.UserRepository;
import com.example.simcheong2.global.exception.model.CustomException;
import com.example.simcheong2.global.exception.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserCreateService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final RedisTemplate<String, String> smsRedisTemplate;

    public void signUp(UserSaveDTO userSaveDTO){
        // 인증이 실패했을 경우 예외처리 해야함.
        String phone = Optional.ofNullable(smsRedisTemplate.opsForValue().get(userSaveDTO.getSessionId()))
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST, "해당 휴대폰 번호로 보낸 인증번호가 없습니다. 인증 요청 먼저 해주세요."));
        log.debug("phone number by input code in redis = {}",phone);
        log.debug("phone number by user input = {}", userSaveDTO.getPhone());
        if(!phone.equals(userSaveDTO.getPhone())){
            throw new CustomException(ErrorCode.BAD_REQUEST,"올바르지 않은 회원가입 요청 : 인증번호가 만료되거나 잘못됨.");
        }
        // 유저 엔티티 생성.
        // DTO 내부에 toEntity 느낌의 함수를 제작하여 사용하는게 캡슐화 면에서 이득...
        User user = User.builder()
                .inputId(userSaveDTO.getInputId())
                .email(userSaveDTO.getEmail())
                .password(passwordEncoder.encode(userSaveDTO.getPassword()))
                .phone(userSaveDTO.getPhone().replaceAll("-",""))
                .sex(userSaveDTO.getSex())
                .birth(userSaveDTO.getBirthday())
                .name(userSaveDTO.getName())
                .nickname(userSaveDTO.getNickname())
                .disabled(userSaveDTO.getIsDisabled())
                .build();

        // 이미 회원이 존재할 경우 에러 처리.
        if(userRepository.findByInputId(userSaveDTO.getInputId()).isPresent()){
            throw new CustomException(ErrorCode.BAD_REQUEST,"중복 아이디 존재.");
        };
        if(userRepository.findByNickname(userSaveDTO.getNickname()).isPresent()){
            throw new CustomException(ErrorCode.BAD_REQUEST,"중복 닉네임 존재.");
        };
        User userResult = null;
        try{
            userResult = userRepository.save(user);
        } catch (DataIntegrityViolationException e){
            log.debug("저장하려는 유저가 존재합니다. User = {}", e.getMessage());
            throw new CustomException(ErrorCode.BAD_REQUEST,"저장하려는 유저 존재.");
        }
    }
}
