package com.example.simcheong2.domain.comment_blame.repository;

import com.example.simcheong2.domain.comment_blame.entity.CommentBlame;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentBlameRepository extends JpaRepository<CommentBlame, Integer> {
}