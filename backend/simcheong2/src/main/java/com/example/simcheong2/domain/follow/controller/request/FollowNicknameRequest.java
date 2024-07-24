package com.example.simcheong2.domain.follow.controller.request;
import com.example.simcheong2.domain.post.controller.response.MyPostInfoResponse;
import com.example.simcheong2.domain.post.controller.response.OtherPostInfoResponse;
import com.example.simcheong2.domain.user.entity.dto.Sex;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowNicknameRequest {
    @NonNull
    @Size(min = 1, max = 20, message = "닉네임 글자 길이는 1글자 이상 20글자 이하여야 합니다.")
    private String nickname;
}
