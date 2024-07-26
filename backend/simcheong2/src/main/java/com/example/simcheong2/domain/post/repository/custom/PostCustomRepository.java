package com.example.simcheong2.domain.post.repository.custom;

import com.example.simcheong2.domain.post.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostCustomRepository {
    Optional<List<Post>> searchPostsExceptUserId(Integer userId);
}
