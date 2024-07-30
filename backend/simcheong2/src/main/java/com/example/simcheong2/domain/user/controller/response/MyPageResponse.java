package com.example.simcheong2.domain.user.controller.response;

import com.example.simcheong2.domain.post.controller.response.MyPostInfoResponse;
import com.example.simcheong2.domain.user.entity.dto.Sex;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MyPageResponse {
    private MyProfileInfoResponse profile;

    private List<MyPostInfoResponse> posts;
}
