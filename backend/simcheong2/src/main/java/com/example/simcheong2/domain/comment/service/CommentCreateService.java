package com.example.simcheong2.domain.comment.service;

import com.example.simcheong2.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CommentCreateService {
    private final CommentRepository commentRepository;
}
