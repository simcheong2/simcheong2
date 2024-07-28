package com.example.simcheong2.global.redis.service;

import com.example.simcheong2.global.exception.model.CustomException;
import com.example.simcheong2.global.exception.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisUtilService {
    private final RedisTemplate redisTemplate;

    @Value("${jwt.token.time}")
    private long ACCESS_TOKEN_EXPIRE_TIME;

    @Value("${jwt.token.refresh-time}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    public String getData(String key) {
        Object result = redisTemplate.opsForValue().get(key);
        if (result != null) {
            return result.toString();
        }
        return null;
    }

    public void setAccessToken(String key, String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, Duration.ofSeconds(ACCESS_TOKEN_EXPIRE_TIME));
    }

    public void setRefreshToken(String key, String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, Duration.ofSeconds(REFRESH_TOKEN_EXPIRE_TIME));
    }

    public void deleteData(String key) {
        if (this.getData(key) == null) throw new CustomException(ErrorCode.BAD_REQUEST, "No data in Redis");
        redisTemplate.delete(key);
    }

}
