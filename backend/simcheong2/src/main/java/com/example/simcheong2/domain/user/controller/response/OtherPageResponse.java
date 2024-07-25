package com.example.simcheong2.domain.user.controller.response;
import com.example.simcheong2.domain.post.controller.response.MyPostInfoResponse;
import com.example.simcheong2.domain.post.controller.response.OtherPostInfoResponse;
import com.example.simcheong2.domain.user.entity.dto.Sex;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtherPageResponse {
    @NonNull
    private OtherProfileInfoResponse profile;

    @NonNull
    private List<OtherPostInfoResponse> posts;
}