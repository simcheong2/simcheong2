package com.example.simcheong2.domain.comment.repository;

import com.example.simcheong2.domain.comment.entity.Comment;
import com.example.simcheong2.domain.comment.repository.custom.CommentCustomRepository;
import com.example.simcheong2.domain.post.repository.custom.PostCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Integer>, CommentCustomRepository {
}
