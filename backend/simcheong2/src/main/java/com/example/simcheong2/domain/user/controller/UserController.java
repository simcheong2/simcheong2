package com.example.simcheong2.domain.user.controller;

import com.example.simcheong2.domain.auth.controller.request.SignupRequest;
import com.example.simcheong2.domain.user.controller.request.UserSearchRequest;
import com.example.simcheong2.domain.user.controller.response.*;
import com.example.simcheong2.domain.user.entity.dto.Sex;
import com.example.simcheong2.domain.user.entity.dto.UserSaveDTO;
import com.example.simcheong2.domain.user.service.UserCreateService;
import com.example.simcheong2.domain.user.service.UserSearchService;
import com.example.simcheong2.domain.user.service.UserUpdateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "유저 정보 API")
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@RestController
public class UserController {
    private final UserSearchService userSearchService;
    private final UserUpdateService userUpdateService;
    private final UserCreateService userCreateService;
    @GetMapping("/search")
    public ResponseEntity<List<UserSearchResponse>> searchUsers(@RequestBody @Valid UserSearchRequest request) {
        return ResponseEntity.ok(new ArrayList<>());
    }
    @PostMapping("/signup")
    public ResponseEntity<Boolean> signup(@RequestBody @Valid SignupRequest request) {
        UserSaveDTO userSaveDTO = new UserSaveDTO(
                request.getId(),
                request.getPassword(),
                request.getEmail(),
                request.getName(),
                request.getNickname(),
                request.getOpeningDate(),
                request.getPhone(),
                request.getIsForeign(),
                request.getIsDisabled(),
                request.getSex()
        );
        userCreateService.signUp(userSaveDTO);
        return ResponseEntity.ok(true);
    }
    @GetMapping("/my-page")
    public ResponseEntity<MyPageResponse> myPage() {
        return ResponseEntity.ok(new MyPageResponse(
                new MyProfileInfoResponse(0, 0, "", "", "", Sex.MALE, true),
                new ArrayList<>())
        );
    }

    @PostMapping("/other-page")
    public ResponseEntity<OtherPageResponse> otherPage() {
        return ResponseEntity.ok(new OtherPageResponse(
                new OtherProfileInfoResponse("", "", true, true, Sex.MALE, true, 0, 0, ""),
                new ArrayList<>()
        ));
    }
}
