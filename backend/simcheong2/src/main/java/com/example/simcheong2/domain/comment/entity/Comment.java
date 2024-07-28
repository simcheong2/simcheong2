package com.example.simcheong2.domain.comment.entity;

import com.example.simcheong2.domain.comment_blame.entity.CommentBlame;
import com.example.simcheong2.domain.post.entity.Post;
import com.example.simcheong2.domain.user.entity.User;
import jakarta.persistence.*;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @Column(length = 200)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @OneToMany(mappedBy = "blamedComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentBlame> blamedCommentCommentBlames;
}
