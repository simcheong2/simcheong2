package com.example.simcheong2.global.service;

import com.example.simcheong2.global.exception.model.CustomException;
import com.example.simcheong2.global.exception.model.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenService {
    private final Key key;

    public JwtTokenService(@Value("${jwt.secret.key}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generate(String subject, String kind, Date expiredAt) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expiredAt)
                .claim("kind", kind)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractSubject(String accessToken){
        try {
            if (!this.validateToken(accessToken)) {
                Claims claims = parseClaims(accessToken);
                return claims.getSubject();
            }
        }
        catch (Exception e){
            throw new CustomException(ErrorCode.BAD_REQUEST, "유효하지 않은 토큰입니다.");
        }
        return parseClaims(accessToken).getSubject();
    }

    private Claims parseClaims(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }

    // 토근 검증
    public Boolean validateToken(String token) throws Exception {
        this.parseClaims(token);
        return true;
    }

    public Long getExpiration(String token) {
        Long expiration = this.parseClaims(token).getExpiration().getTime();
        Long now = new Date().getTime();
        return (expiration - now) / 1000;
    }

    public Authentication getAuthentication(com.example.simcheong2.domain.user.entity.User user1) {
        // getKey 는 뺌...
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user1.getUserId().toString())
                .password(user1.getPassword())
                .build();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

}
