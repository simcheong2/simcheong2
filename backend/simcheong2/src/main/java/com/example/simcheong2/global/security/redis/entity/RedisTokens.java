package com.example.simcheong2.global.security.redis.entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Builder
@Getter
@RedisHash(value = "redisRefreshToken", timeToLive = 3600)
@AllArgsConstructor
@NoArgsConstructor
public class RedisTokens {
    @Id
    private String id;
    private String refreshToken;
    private String accessToken;
}
