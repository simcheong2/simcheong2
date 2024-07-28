package com.example.simcheong2.domain.user.service;

import com.example.simcheong2.domain.user.entity.User;
import com.example.simcheong2.domain.user.entity.dto.UserDTO;
import com.example.simcheong2.domain.user.repository.UserRepository;
import com.example.simcheong2.global.exception.model.CustomException;
import com.example.simcheong2.global.exception.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserValidationService {
    private final UserRepository userRepository;

    public Optional<UserDTO> isPhoneNumberAlreadyRegistered(String phone) {
        return userRepository.findByPhone(phone)
                .map(user -> UserDTO.builder()
                        .name(user.getName())
                        .nickname(user.getNickname())
                        .phone(user.getPhone())
                        .build());
    }
}