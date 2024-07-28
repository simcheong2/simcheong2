package com.example.simcheong2.domain.user.entity;

import com.example.simcheong2.domain.comment.entity.Comment;
import com.example.simcheong2.domain.comment_blame.entity.CommentBlame;
import com.example.simcheong2.domain.post_blame.entity.PostBlame;
import com.example.simcheong2.domain.user_blame.entity.UserBlame;
import com.example.simcheong2.domain.user_post_like.entity.UserPostLike;
import com.example.simcheong2.domain.follow.entity.Follow;
import com.example.simcheong2.domain.post.entity.Post;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
public class User {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(length = 100,unique = true)
    private String inputId;

    @Column(length = 100)
    private String email;

    @Column(length = 200)
    private String password;

    @Column(length = 11)
    private String phone;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean gender;

    @Column
    private Date birth;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100,unique = true)
    private String nickname;

    @Column(length = 100)
    private String introduce;

    @Column(columnDefinition = "longtext")
    private String profileImage;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean disabled;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean postVisible;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> userPosts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private Set<Comment> userComments;

    @OneToMany(mappedBy = "user")
    private Set<UserPostLike> userUserPostLikes;

    @OneToMany(mappedBy = "follower")
    private Set<Follow> followerFollows;

    @OneToMany(mappedBy = "following")
    private Set<Follow> followingFollows;

    @OneToMany(mappedBy = "blamer")
    private Set<UserBlame> blamerUserBlames;

    @OneToMany(mappedBy = "blamedUser")
    private Set<UserBlame> blamedUserUserBlames;

    @OneToMany(mappedBy = "blamer")
    private Set<CommentBlame> blamerCommentBlames;

    @OneToMany(mappedBy = "blamer")
    private Set<PostBlame> blamerPostBlames;

    public User(Integer userId, String inputId, String email, String password, String phone, Boolean gender, Date birth, String name, String nickname, String introduce, String profileImage, Boolean disabled, Boolean postVisible, List<Post> userPosts, Set<Comment> userComments, Set<UserPostLike> userUserPostLikes, Set<Follow> followerFollows, Set<Follow> followingFollows, Set<UserBlame> blamerUserBlames, Set<UserBlame> blamedUserUserBlames, Set<CommentBlame> blamerCommentBlames, Set<PostBlame> blamerPostBlames) {
        this.userId = userId;
        this.inputId = inputId;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.birth = birth;
        this.name = name;
        this.nickname = nickname;
        this.introduce = introduce;
        this.profileImage = profileImage;
        this.disabled = disabled;
        this.postVisible = postVisible;
        this.userPosts = userPosts;
        this.userComments = userComments;
        this.userUserPostLikes = userUserPostLikes;
        this.followerFollows = followerFollows;
        this.followingFollows = followingFollows;
        this.blamerUserBlames = blamerUserBlames;
        this.blamedUserUserBlames = blamedUserUserBlames;
        this.blamerCommentBlames = blamerCommentBlames;
        this.blamerPostBlames = blamerPostBlames;
    }

    public void addPost(Post post) {
        this.userPosts.add(post);
        post.updateUser(this);
    }
}
