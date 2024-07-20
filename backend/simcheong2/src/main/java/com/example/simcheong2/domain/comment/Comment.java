package com.example.simcheong2.domain.comment;

import com.example.simcheong2.domain.comment_blame.CommentBlame;
import com.example.simcheong2.domain.post.Post;
import com.example.simcheong2.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Builder(toBuilder = true)
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

    @OneToMany(mappedBy = "blamedComment")
    private Set<CommentBlame> blamedCommentCommentBlames;

    public Comment(Integer commentId, String content, User user, Post post, Set<CommentBlame> blamedCommentCommentBlames) {
        this.commentId = commentId;
        this.content = content;
        this.user = user;
        this.post = post;
        this.blamedCommentCommentBlames = blamedCommentCommentBlames;
    }
}
