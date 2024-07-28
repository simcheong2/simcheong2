package com.example.simcheong2.global.redis.service;

import com.example.simcheong2.global.exception.model.CustomException;
import com.example.simcheong2.global.exception.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public String getData(String key){
        Object result = redisTemplate.opsForValue().get(key);
        if(result != null){
            return result.toString();
        }
        return null;
    }

    public void setData(String key, String value){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        if(!valueOperations.setIfAbsent(key, value, Duration.ofSeconds(3600))){
            throw new CustomException(ErrorCode.BAD_REQUEST,"Duplication login");
        }
    }

    public void setAccessToken(String key, String value){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value,Duration.ofSeconds(43200));
        /*if(!valueOperations.setIfAbsent(key, value, Duration.ofSeconds(43200))){
            throw new CustomException(ErrorCode.BAD_REQUEST,"Duplication login");
        }
         */
    }

    public void setRefreshToken(String key, String value){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value,Duration.ofSeconds(1209600));
        /*if(!valueOperations.setIfAbsent(key, value, Duration.ofSeconds(1209600))){
            throw new CustomException(ErrorCode.BAD_REQUEST,"Duplication login");
        }
         */
    }


    public void setDataExpire(String key, String value, Long duration){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public void deleteData(String key){
        if(this.getData(key) == null) throw new CustomException(ErrorCode.BAD_REQUEST,"No data in Redis");
        redisTemplate.delete(key);
    }

}
