package com.example.simcheong2.domain.user.controller;

import com.example.simcheong2.global.ai.OpenAiHelper;
import com.example.simcheong2.global.file.FileHelper;

import com.example.simcheong2.domain.user.controller.response.*;
import com.example.simcheong2.domain.user.entity.dto.UserSearchDTO;
import com.example.simcheong2.domain.user.service.UserCreateService;
import com.example.simcheong2.domain.user.service.UserSearchService;
import com.example.simcheong2.domain.user.service.UserUpdateService;
import com.example.simcheong2.global.service.SecurityUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "유저 정보 API")
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@RestController
public class UserController {
    private final UserSearchService userSearchService;
    private final UserUpdateService userUpdateService;
    private final UserCreateService userCreateService;
    private final FileHelper fileHelper;
    private final OpenAiHelper openAiHelper;


    @GetMapping("/search")
    public ResponseEntity<List<UserSearchResponse>> searchUsers(@RequestParam(value = "nickname") String nickname) {
        int userId = SecurityUtil.getCurrentUserId();
        List<UserSearchDTO> response = userSearchService.searchNickname(userId, nickname);
        return ResponseEntity.ok(response.stream()
                .map(UserSearchDTO::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/my-page")
    public ResponseEntity<MyPageResponse> myPage() {
        int userId = SecurityUtil.getCurrentUserId();
        MyPageResponse response = userSearchService.getMyPageInfo(userId).toResponse();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/other-page")
    public ResponseEntity<OtherPageResponse> otherPage(@RequestParam("nickname") String nickname) {
        int userId = SecurityUtil.getCurrentUserId();

        OtherPageResponse response = userSearchService.getOtherPageInfo(nickname, userId).toResponse();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/profile-url")
    public ResponseEntity<ProfileChangeResponse> changeProfileUrl(
            @RequestPart MultipartFile image
    ) {
        int userId = SecurityUtil.getCurrentUserId();
        String url = userUpdateService.updateProfileUrl(userId, image);
        return ResponseEntity.ok(ProfileChangeResponse.builder()
                .profileUrl(url)
                .build());
    }

    @PostMapping("/audio")
    public ResponseEntity<String> getAudio(
            HttpServletRequest servletRequest,
            @RequestPart MultipartFile audio
    ) {
        String uploadDirRealPath = servletRequest.getSession().getServletContext().getRealPath("/upload/"); // 저장 디렉토리 경로
        String transcription = openAiHelper.getTextFromAudioMultipartFile(audio, uploadDirRealPath);
        return ResponseEntity.ok(transcription);
    }
}
