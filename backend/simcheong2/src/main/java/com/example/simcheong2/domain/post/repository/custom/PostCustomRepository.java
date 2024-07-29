package com.example.simcheong2.domain.post.repository.custom;

import com.example.simcheong2.domain.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostCustomRepository {
    Optional<List<Post>> searchPostsExceptUserId(Integer userId);

    Optional<List<Post>> findRecentPosts(Integer userId);
}
