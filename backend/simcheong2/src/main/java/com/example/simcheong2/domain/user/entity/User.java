package com.example.simcheong2.domain.user.entity;

import com.example.simcheong2.domain.comment.entity.Comment;
import com.example.simcheong2.domain.comment_blame.entity.CommentBlame;
import com.example.simcheong2.domain.post_blame.entity.PostBlame;
import com.example.simcheong2.domain.user.entity.dto.Sex;
import com.example.simcheong2.domain.user_blame.entity.UserBlame;
import com.example.simcheong2.domain.user_post_like.entity.UserPostLike;
import com.example.simcheong2.domain.follow.entity.Follow;
import com.example.simcheong2.domain.post.entity.Post;
import com.example.simcheong2.global.entity.BaseEntity;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(length = 100, unique = true, nullable = false)
    private String inputId;

    @Column(length = 100)
    private String email;

    @Column(length = 200, nullable = false)
    private String password;

    @Column(length = 11, nullable = false)
    private String phone;

    @Column
    private Date birth;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING) // EnumType.ORDINAL을 사용하면 숫자로 저장됩니다.
    @Column
    private Sex sex;

    @Column(length = 100)
    private String introduce;

    @Column(columnDefinition = "text")
    private String profileImage;

    @Column(columnDefinition = "tinyint", length = 1)
    @Builder.Default
    @ColumnDefault("1")
    private Boolean disabled = true;

    @Column(columnDefinition = "tinyint", length = 1)
    @Builder.Default
    @ColumnDefault("1")
    private Boolean postVisible = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> userPosts = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> userComments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserPostLike> userUserPostLikes;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> followerFollows;

    // 내가 팔로잉 당하고 있는 입장으로 Follow 테이블에 들어간 것
    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> followingFollows;

    @OneToMany(mappedBy = "blamer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserBlame> blamerUserBlames;

    @OneToMany(mappedBy = "blamedUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserBlame> blamedUserUserBlames;

    @OneToMany(mappedBy = "blamer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentBlame> blamerCommentBlames;

    @OneToMany(mappedBy = "blamer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostBlame> blamerPostBlames;

    public void addPost(Post post) {
        this.userPosts.add(post);
        post.updateUser(this);
    }

    // 너가 얘 other를 팔로우 하고 있니?
    public boolean isFollow(User other) {
        return followerFollows.stream().anyMatch(
                follow -> follow.getFollowing().getUserId() == other.getUserId()
        );
    }

    // other가 너 게시물에 좋아요 누를 수 있니?
    public boolean isPossibleLike(User other) {
        if (this.postVisible) return true;
        if (this.isFollow(other)) return true;
        return false;
    }

    public boolean isReported(){
        return blamedUserUserBlames.stream().anyMatch(
                userBlame -> userBlame.getBlamedUser().getUserId() == this.userId
        );

    public void updateProfileUrl(String profileUrl) {
        this.profileImage = profileUrl;
    }
}
