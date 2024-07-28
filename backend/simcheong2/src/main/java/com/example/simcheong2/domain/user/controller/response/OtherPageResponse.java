package com.example.simcheong2.domain.user.controller.response;

import com.example.simcheong2.domain.post.controller.response.OtherPostInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
