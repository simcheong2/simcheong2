package com.example.simcheong2.domain.post.controller.reqeust;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostContentRequest {
    @NonNull
    private String content;
}
