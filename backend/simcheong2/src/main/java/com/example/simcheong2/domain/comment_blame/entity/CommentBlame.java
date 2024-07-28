package com.example.simcheong2.domain.comment_blame.entity;

import com.example.simcheong2.domain.comment.entity.Comment;
import com.example.simcheong2.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CommentBlame {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentBlameId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blamed_comment_id", nullable = false)
    private Comment blamedComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blamer_id", nullable = false)
    private User blamer;
}
