package com.example.simcheong2.global.service;

import com.example.simcheong2.global.exception.model.CustomException;
import com.example.simcheong2.global.exception.model.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class SecurityUtil {


    private SecurityUtil() {
    }

    // SecurityContext에 있는 Authentication을 꺼내서, username을 찾아 반환해줌.
    // JWTFilter의 doFilter메소드에서 SecurityContext에 Authentication 객체를 저장해놓음.
    public static Integer getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.debug("Security Context에 인증 정보가 없습니다.");
            throw new CustomException(ErrorCode.BAD_REQUEST, "유저 정보 조회 불가");
        }

        Integer userId = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            userId = Integer.valueOf(springSecurityUser.getUsername());
        } else if (authentication.getPrincipal() instanceof String) {
            userId = Integer.valueOf((String) authentication.getPrincipal());
        }

        return userId;
    }
}