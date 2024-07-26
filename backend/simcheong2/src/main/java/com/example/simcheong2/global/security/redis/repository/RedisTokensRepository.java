package com.example.simcheong2.global.security.redis.repository;

import com.example.simcheong2.global.security.redis.entity.RedisTokens;
import org.springframework.data.repository.CrudRepository;

public interface RedisTokensRepository extends CrudRepository<RedisTokens, String> {
}
