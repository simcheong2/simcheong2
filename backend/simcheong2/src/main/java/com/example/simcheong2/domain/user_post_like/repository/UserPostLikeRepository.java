package com.example.simcheong2.domain.user_post_like.repository;

import com.example.simcheong2.domain.user_post_like.entity.UserPostLike;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserPostLikeRepository extends JpaRepository<UserPostLike, Integer> {
}
