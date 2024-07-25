package com.example.simcheong2.domain.post.service;

import com.example.simcheong2.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostValidationService {
    private final PostRepository postRepository;

    private static final long MAX_FILE_SIZE = 20 * 1024 * 1024; // 5MB

    public boolean isFileSizeWithinLimit(MultipartFile file) {
        long fileSize = file.getSize();
        return fileSize <= MAX_FILE_SIZE;
    }

    public boolean isFileSizeWithinLimit(List<MultipartFile> files) {
        for (MultipartFile file : files) {
            long fileSize = file.getSize();
            log.info("파일 크기: {}", fileSize);
            if (fileSize > MAX_FILE_SIZE) return false;
        }
        return true;
    }
}
