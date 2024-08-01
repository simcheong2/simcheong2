package com.example.simcheong2.global.security.filter;

import com.example.simcheong2.domain.user.entity.User;
import com.example.simcheong2.domain.user.repository.UserRepository;
import com.example.simcheong2.global.exception.model.CommonExceptionResponse;
import com.example.simcheong2.global.redis.service.RedisUtilService;
import com.example.simcheong2.global.service.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
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
import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
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
                        this.sendResponse(response, "접근 권한이 없습니다.","NOT_HAVE_AUTHORIZATION" ,HttpStatus.FORBIDDEN);
                        return;
                    }
                } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
                    this.sendResponse(response, "유효하지 않은 accessToken 입니다.","NOT_HAVE_AUTHORIZATION" ,HttpStatus.UNAUTHORIZED);
                    return;
                } catch (ExpiredJwtException e) {
                    this.sendResponse(response, "만료된 accessToken 입니다.","NOT_HAVE_AUTHORIZATION" ,HttpStatus.UNAUTHORIZED);
                    return;
                } catch (UnsupportedJwtException e) {
                    this.sendResponse(response, "지원되지 않는 accessToken 입니다.","NOT_HAVE_AUTHORIZATION" ,HttpStatus.BAD_REQUEST);
                    return;
                } catch (IllegalArgumentException e) {
                    this.sendResponse(response, "JWT claim string 이 비어 있습니다.","NOT_HAVE_AUTHORIZATION" ,HttpStatus.BAD_REQUEST);
                    return;
                } catch (Exception e){
                    log.error("기타 Exception: {}",e.getMessage());
                }
            } else {
                this.sendResponse(response, "Authorization 포멧이 맞지 않습니다","INVALID_REQUEST" ,HttpStatus.BAD_REQUEST);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
    private boolean checkAccessToken(String accessToken) throws Exception{
        if(!(accessToken==null && accessToken.isEmpty())){
            if(jwtTokenService.validateToken(accessToken)){
                String inputId = jwtTokenService.extractSubject(accessToken);
                Optional<User> exist = userRepository.findByInputId(inputId);
                if(exist.isEmpty()){
                    log.debug("{} 의 user 를 찾을수 없습니다.",accessToken);
                    return false;
                }
                User user = exist.get();
                Authentication authentication = jwtTokenService.getAuthentication(user);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return true;
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
