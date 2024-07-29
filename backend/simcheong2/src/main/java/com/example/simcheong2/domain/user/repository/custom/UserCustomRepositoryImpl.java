package com.example.simcheong2.domain.user.repository.custom;

import com.example.simcheong2.domain.follow.entity.QFollow;
import com.example.simcheong2.domain.user.entity.User;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import static com.example.simcheong2.domain.comment.entity.QComment.comment;
import static com.example.simcheong2.domain.follow.entity.QFollow.*;
import static com.example.simcheong2.domain.image.entity.QImage.*;
import static com.example.simcheong2.domain.post.entity.QPost.*;
import static com.example.simcheong2.domain.user.entity.QUser.user;
import static com.example.simcheong2.domain.user_post_like.entity.QUserPostLike.userPostLike;

@Slf4j
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<User> getMyPageInfo(int userId) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(user)
                        .leftJoin(user.followingFollows).fetchJoin()
                        .leftJoin(user.followerFollows).fetchJoin()
                        .leftJoin(user.userPosts, post).fetchJoin()
                        .leftJoin(post.postImages, image).fetchJoin()
                        .leftJoin(post.postUserPostLikes, userPostLike).fetchJoin()
                        .leftJoin(post.postComments, comment).fetchJoin()
                        .where(user.userId.eq(userId))
                        .fetchOne()
        );
    }

    @Override
    public Optional<List<User>> getMyFollows(int userId) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(user)
                        .where(user.userId.in(
                                JPAExpressions
                                        .select(follow.following.userId)
                                        .from(follow)
                                        .where(follow.follower.userId.eq(userId))
                        ))
                        .fetch()
        );
    }


}
