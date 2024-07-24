package com.example.simcheong2.domain.auth.service;
import com.example.simcheong2.domain.user.service.UserCreateService;
import com.example.simcheong2.domain.user.service.UserDeleteService;
import com.example.simcheong2.domain.user.service.UserValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserValidationService userValidationService;
    private final UserCreateService userCreateService;
    private final UserDeleteService userDeleteService;
}
