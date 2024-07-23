package com.example.simcheong2.domain.comment.repository;

import com.example.simcheong2.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
