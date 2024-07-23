package com.example.simcheong2.domain.post.entity;

import com.example.simcheong2.domain.comment.entity.Comment;
import com.example.simcheong2.domain.image.entity.Image;
import com.example.simcheong2.domain.post_blame.entity.PostBlame;
import com.example.simcheong2.domain.user.entity.User;
import com.example.simcheong2.domain.user_post_like.entity.UserPostLike;
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


@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
public class Post {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(columnDefinition = "longtext")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post")
    private Set<Comment> postComments;

    @OneToMany(mappedBy = "post")
    private Set<Image> postImages;

    @OneToMany(mappedBy = "post")
    private Set<UserPostLike> postUserPostLikes;

    @OneToMany(mappedBy = "blamedPost")
    private Set<PostBlame> blamedPostPostBlames;

    public Post(Integer postId, String content, User user, Set<Comment> postComments, Set<Image> postImages, Set<UserPostLike> postUserPostLikes, Set<PostBlame> blamedPostPostBlames) {
        this.postId = postId;
        this.content = content;
        this.user = user;
        this.postComments = postComments;
        this.postImages = postImages;
        this.postUserPostLikes = postUserPostLikes;
        this.blamedPostPostBlames = blamedPostPostBlames;
    }
}
