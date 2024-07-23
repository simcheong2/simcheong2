package com.example.simcheong2.domain.post_blame;

import com.example.simcheong2.domain.post.entity.Post;
import com.example.simcheong2.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
public class PostBlame {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postBlameId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blamed_post_id", nullable = false)
    private Post blamedPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blamer_id", nullable = false)
    private User blamer;

    public PostBlame(Integer postBlameId, Post blamedPost, User blamer) {
        this.postBlameId = postBlameId;
        this.blamedPost = blamedPost;
        this.blamer = blamer;
    }
}
