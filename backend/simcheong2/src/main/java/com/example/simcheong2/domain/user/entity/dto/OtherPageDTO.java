package com.example.simcheong2.domain.user.entity.dto;


import com.example.simcheong2.domain.post.entity.dto.MyPostInfoDTO;
import com.example.simcheong2.domain.post.entity.dto.OtherPostInfoDTO;
import com.example.simcheong2.domain.user.controller.response.MyPageResponse;
import com.example.simcheong2.domain.user.controller.response.MyProfileInfoResponse;
import com.example.simcheong2.domain.user.controller.response.OtherPageResponse;
import com.example.simcheong2.domain.user.controller.response.OtherProfileInfoResponse;
import com.example.simcheong2.domain.user.entity.User;
import com.example.simcheong2.domain.user.repository.UserRepository;
import com.example.simcheong2.global.exception.model.CustomException;
import com.example.simcheong2.global.exception.model.ErrorCode;
import com.example.simcheong2.global.service.SecurityUtil;
import lombok.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class OtherPageDTO {
    private Integer followingCount; // 다른사람을 팔로우하는 사람들
    private Integer followerCount; // 다른사람이 팔로우한 사람들
    private String email;
    private String profileUrl;
    private String nickname;
    private Sex sex; // MALE, FEMALE 스트링이 입력으로 들어오면 자동으로 매칭 됨.
    private Boolean isDisabled;
    private Boolean isReported;

    private Boolean isFollow;
    // "내"가 그사람 팔로우 하는지 -> 그사람의 팔로워 목록에서 나를 찾으면 됨.
    private List<OtherPostInfoDTO> posts;

    public static OtherPageDTO from(User other, User me) {
        if(other.equals(me)){
            throw new CustomException(ErrorCode.BAD_REQUEST,"다른사람 페이지 불러오는 중에 다른사람 정보 = 자신의 정보인 오류 발생");
        }
        return OtherPageDTO.builder()
                .followingCount(other.getFollowingFollows().size())
                .followerCount(other.getFollowerFollows().size())
                .email(other.getEmail())
                .profileUrl(other.getProfileImage())
                .nickname(other.getNickname())
                .sex(other.getSex())
                .isDisabled(other.getDisabled())
                .isReported(other.isReported())
                .isFollow(me.isFollow(other))
                .posts(other.getUserPosts().stream()
                        .map(OtherPostInfoDTO::from)
                        .collect(Collectors.toList()))
                .build();
    }

    public OtherPageResponse toResponse() {
        return OtherPageResponse.builder()
                .posts(posts.stream()
                        .sorted(Comparator.comparing(OtherPostInfoDTO::getCreatedDate).reversed())
                        .map(OtherPostInfoDTO::toResponse)
                        .collect(Collectors.toList()))
                .profile(OtherProfileInfoResponse.builder()
                        .followingCount(followingCount)
                        .followerCount(followerCount)
                        .email(email)
                        .profileUrl(profileUrl)
                        .nickname(nickname)
                        .sex(sex)
                        .isDisabled(isDisabled)
                        .isReported(isReported)
                        .isFollow(isFollow)
                        .build())
                .build();
    }
}
