package com.example.simcheong2.domain.post.entity;

import com.example.simcheong2.domain.comment.entity.Comment;
import com.example.simcheong2.domain.image.entity.Image;
import com.example.simcheong2.domain.post_blame.entity.PostBlame;
import com.example.simcheong2.domain.user.entity.User;
import com.example.simcheong2.domain.user_post_like.entity.UserPostLike;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> postImages;

    @OneToMany(mappedBy = "post")
    private Set<UserPostLike> postUserPostLikes;

    @OneToMany(mappedBy = "blamedPost")
    private Set<PostBlame> blamedPostPostBlames;

    public Post(String content, List<Image> postImages) {
        this.content = content;
        this.postImages = postImages;
        postImages.forEach(image -> image.updatePost(this));
    }

    public void updateUser(User user) {
        this.user = user;
    }
}