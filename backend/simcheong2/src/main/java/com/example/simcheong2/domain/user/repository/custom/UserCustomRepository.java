package com.example.simcheong2.domain.user.repository.custom;

import com.example.simcheong2.domain.user.entity.User;

import java.util.Optional;

public interface UserCustomRepository {
    Optional<User> getMyPageInfo(int userId);

    Optional<User> getOtherPageInfo(String nickname);
}
