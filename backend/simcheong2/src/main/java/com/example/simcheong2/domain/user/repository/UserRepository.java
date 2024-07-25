package com.example.simcheong2.domain.user.repository;

import com.example.simcheong2.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserId(int userId);

    Optional<User> findByPhone(String phone);
}
