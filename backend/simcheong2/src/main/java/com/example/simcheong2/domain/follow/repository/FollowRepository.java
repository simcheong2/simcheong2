package com.example.simcheong2.domain.follow.repository;

import com.example.simcheong2.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FollowRepository extends JpaRepository<Follow, Integer> {
}
