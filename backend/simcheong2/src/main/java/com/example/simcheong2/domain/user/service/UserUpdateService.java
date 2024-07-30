package com.example.simcheong2.domain.user.service;

import com.example.simcheong2.domain.user.entity.User;
import com.example.simcheong2.domain.user.repository.UserRepository;
import com.example.simcheong2.global.exception.model.CustomException;
import com.example.simcheong2.global.exception.model.ErrorCode;
import com.example.simcheong2.global.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserUpdateService {
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    public String updateProfileUrl(int userId, MultipartFile profileMultipartFile) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "유저를 찾을 수 없습니다."));

        String s3Url = uploadProfileImage(profileMultipartFile);
        user.updateProfileUrl(s3Url);
        return s3Url;
    }

    private String uploadProfileImage(MultipartFile multipartFile) {
        try {
            String s3Url = s3Uploader.uploadFile(multipartFile, "static");
            return s3Url;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "프로필 이미지 업로드 과정에서 문제가 발생했습니다. 다른 사진 혹은 잠시 후 다시 시도해주세요.");
        }
    }
}
