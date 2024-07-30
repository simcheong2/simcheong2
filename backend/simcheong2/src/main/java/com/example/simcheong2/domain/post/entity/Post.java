package com.example.simcheong2.domain.post.entity;

import com.example.simcheong2.domain.comment.entity.Comment;
import com.example.simcheong2.domain.image.entity.Image;
import com.example.simcheong2.domain.post_blame.entity.PostBlame;
import com.example.simcheong2.domain.user.entity.User;
import com.example.simcheong2.domain.user_post_like.entity.UserPostLike;
import com.example.simcheong2.global.entity.BaseEntity;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(columnDefinition = "text")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> postComments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> postImages;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserPostLike> postUserPostLikes;

    @OneToMany(mappedBy = "blamedPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostBlame> blamedPostPostBlames;

    public Post(String content, Set<Image> postImages) {
        this.content = content;
        this.postImages = postImages;
        postImages.forEach(image -> image.updatePost(this));
    }

    public void updateUser(User user) {
        this.user = user;
    }

    public boolean isSelfLiked() {
        return postUserPostLikes.stream().anyMatch(
                userPostLike -> userPostLike.getUser().getUserId() == this.user.getUserId()
        );
    }

    public boolean isReported(){
        return blamedPostPostBlames.stream().anyMatch(
                postBlame -> postBlame.getBlamedPost().getPostId() == this.postId
        );
    }
}
