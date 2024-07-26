package com.example.simcheong2.global.security.filter;

import com.example.simcheong2.domain.user.entity.User;
import com.example.simcheong2.domain.user.repository.UserRepository;
import com.example.simcheong2.global.exception.model.CommonExceptionResponse;
import com.example.simcheong2.global.security.redis.service.RedisUtilService;
import com.example.simcheong2.global.service.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final RedisUtilService redisUtilService;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    @Override
    protected void doFilterInternal (
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    )throws IOException, ServletException {
        String regexp = "^(Bearer)\s.+$";
        String Tokens = request.getHeader("Authorization");
        if(Tokens != null){
            if(Tokens.matches(regexp)){
                String accessToken = Tokens.substring(7);
                try{
                    if(!this.checkAccessToken(accessToken)){
                        this.sendResponse(response, "접근 권한이 없습니다.","NOT_HAVE_AUTHORIZATION" ,HttpStatus.UNAUTHORIZED);
                        return;
                    }
                } catch (Exception e){
                    log.debug("doFilterInternal = {}", e.getMessage());
                }
            } else {
                this.sendResponse(response, "Authorization 포멧이 맞지 않습니다","INVALID_REQUEST" ,HttpStatus.BAD_REQUEST);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
    private boolean checkAccessToken(String accessToken){
        String inputId = jwtTokenService.extractSubject(accessToken);
        User user = userRepository.findByInputId(inputId).get();
        //ispresent +
        Integer memberId = user.getUserId();
        String isLogout = redisUtilService.getData(accessToken);
        //String refreshToken = redisUtilService.getData(memberId.toString());
        String isLogin = redisUtilService.getData(user.getInputId());
        log.debug("isLogout = {}, isLogin = {}", isLogout, isLogin);

        if(!(accessToken==null && accessToken.isEmpty()) && isLogout == null && isLogin != null){
            try {
                if(jwtTokenService.validateToken(accessToken)){
                    Authentication authentication = jwtTokenService.getAuthentication(user);
                    log.debug("authentication = {}", authentication);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return true;
                }
            } catch (Exception e){
                log.debug("checkAccessToken fail = {}", e.getMessage());
                return false;
            }
        }
        return false;
    }
    private void sendResponse(HttpServletResponse response, String message, String code, HttpStatus status) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        CommonExceptionResponse result = new CommonExceptionResponse(code, message);

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(result));
    }
}
