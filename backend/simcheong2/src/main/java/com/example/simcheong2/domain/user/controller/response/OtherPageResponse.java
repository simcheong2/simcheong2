package com.example.simcheong2.domain.user.controller.response;

import com.example.simcheong2.domain.post.controller.response.OtherPostInfoResponse;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OtherPageResponse {
    private OtherProfileInfoResponse profile;

    private List<OtherPostInfoResponse> posts;
}
