package com.example.simcheong2.global.redis.repository;

import com.example.simcheong2.global.redis.entity.RedisTokens;
import org.springframework.data.repository.CrudRepository;

public interface RedisTokensRepository extends CrudRepository<RedisTokens, String> {
}
