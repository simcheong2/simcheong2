package com.example.simcheong2.domain.user.repository;

import com.example.simcheong2.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
}
