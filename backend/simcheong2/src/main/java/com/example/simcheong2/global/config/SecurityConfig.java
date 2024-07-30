package com.example.simcheong2.global.config;

import com.example.simcheong2.global.security.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.debug("securityFilterChain start");
        http.csrf((csrf) -> csrf.disable())
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/auth/login","/auth/signup","/auth/logout","/auth/sms-verifications","/auth/sms-code").permitAll()
                        .requestMatchers("/v3/**", "/swagger-ui/**").permitAll()
                        // 임시 테스트 할때는 anyRequest().permitAll()로 잠시.
                        .anyRequest().authenticated()
                )
                .headers((headers) -> headers
                        .frameOptions(frameOptions -> frameOptions
                                .sameOrigin()
                        )
                );
        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        log.debug("webSecurityCustomizer start");
//        return web -> web.ignoring().requestMatchers("/auth/**");
//    }
}
