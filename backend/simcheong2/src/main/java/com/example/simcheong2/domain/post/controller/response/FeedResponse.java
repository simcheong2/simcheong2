package com.example.simcheong2.domain.post.controller.response;

import com.example.simcheong2.domain.comment.controller.response.CommentResponse;
import com.example.simcheong2.domain.image.controller.response.ImagesResponse;
import com.example.simcheong2.domain.user.controller.response.OtherUserInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedResponse {
    @NonNull
    private OtherUserInfoResponse otherUserInfoResponse;

    @NonNull
    private OtherPostInfoResponse posts;

    @NonNull
    private List<CommentResponse> comments;
}

