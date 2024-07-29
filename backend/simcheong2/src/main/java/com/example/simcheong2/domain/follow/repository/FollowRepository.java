package com.example.simcheong2.domain.follow.repository;

import com.example.simcheong2.domain.follow.entity.Follow;
import com.example.simcheong2.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface FollowRepository extends JpaRepository<Follow, Integer> {
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
}
