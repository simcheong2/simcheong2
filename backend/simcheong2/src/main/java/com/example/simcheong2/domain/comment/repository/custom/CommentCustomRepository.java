package com.example.simcheong2.domain.comment.repository.custom;

import com.example.simcheong2.domain.comment.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentCustomRepository {
    Optional<List<Comment>> searchCommentByPostId(Integer postId);
}
