package com.example.simcheong2.domain.post.repository;

import com.example.simcheong2.domain.post.entity.Post;
import com.example.simcheong2.domain.post.repository.custom.PostCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Integer>, PostCustomRepository {
    Optional<Post> findByPostId(int postId);
}
