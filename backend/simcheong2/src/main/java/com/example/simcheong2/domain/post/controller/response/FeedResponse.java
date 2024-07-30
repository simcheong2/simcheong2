package com.example.simcheong2.domain.post.controller.response;

import com.example.simcheong2.domain.comment.controller.response.CommentResponse;
import com.example.simcheong2.domain.image.controller.response.ImagesResponse;
import com.example.simcheong2.domain.user.controller.response.OtherUserInfoResponse;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class FeedResponse {
    private OtherUserInfoResponse otherUserInfoResponse;

    private OtherPostInfoResponse posts;

    private List<CommentResponse> comments;
}

