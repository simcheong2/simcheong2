package com.example.simcheong2.domain.user.service;

import com.example.simcheong2.domain.post.service.PostSearchService;
import com.example.simcheong2.domain.user.entity.User;
import com.example.simcheong2.domain.user.entity.dto.MyPageDTO;
import com.example.simcheong2.domain.user.entity.dto.OtherPageDTO;
import com.example.simcheong2.domain.user.entity.dto.UserSearchDTO;
import com.example.simcheong2.domain.user.repository.UserRepository;
import com.example.simcheong2.global.exception.model.CustomException;
import com.example.simcheong2.global.exception.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserSearchService {
    private final UserValidationService userValidationService;
    private final UserRepository userRepository;
    private final PostSearchService postSearchService;

    public MyPageDTO getMyPageInfo(int userId) {
        return userRepository.getMyPageInfo(userId)
                .map(MyPageDTO::from)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "등록된 유저가 아니거나 유저 정보를 불러올 수 없습니다."));
    }

    public OtherPageDTO getOtherPageInfo(String nickname, Integer myID) {
        User me = userRepository.findByUserId(myID).get();
        return userRepository.getOtherPageInfo(nickname)
                .map(user -> OtherPageDTO.from(user, me))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "등록된 유저가 아니거나 유저 정보를 불러올 수 없습니다."));
    }

    public List<UserSearchDTO> searchNickname(int userId, String nickname) {
        List<User> users = userRepository.getAllUsersStartsWithNicknameAndNotInUserId(nickname, userId)
                .orElse(new ArrayList<>());

        return users.stream()
                .map(UserSearchDTO::from)
                .collect(Collectors.toList());
    }
}
