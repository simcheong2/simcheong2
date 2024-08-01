package com.example.simcheong2.domain.post.repository.custom;

import com.example.simcheong2.domain.post.entity.Post;
import com.example.simcheong2.domain.post.entity.QPost;
import com.querydsl.jpa.JPAExpressions;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import static com.example.simcheong2.domain.follow.entity.QFollow.follow;
import static com.example.simcheong2.domain.post.entity.QPost.post;
import static com.example.simcheong2.domain.user.entity.QUser.user;

@Slf4j
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<List<Post>> searchPostsExceptUserId(Integer userId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(post)
                        .leftJoin(post.user).fetchJoin()
                        .leftJoin(post.user.followerFollows).fetchJoin()
                        .leftJoin(post.user.blamedUserUserBlames).fetchJoin() // 이 게시물 주인이 피신고자로써 신고당한 신고기록들
                        .leftJoin(post.postImages).fetchJoin()
                        .leftJoin(post.postUserPostLikes).fetchJoin()
                        .leftJoin(post.postComments).fetchJoin()
                        .leftJoin(post.blamedPostPostBlames).fetchJoin()
                        .where(post.user.followingFollows.any().follower.userId.eq(userId)) // 자기 팔로워들것만 가져오도록
                        .orderBy(post.createdDate.desc()) // 최신 게시글 먼저 뜨도록
                        .fetch()
        );
    }

    @Override
    public Optional<List<Post>> findRecentPosts(Integer userId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(post)
                        .where(
                                post.user.postVisible.eq(true)
                                        .and(post.user.userId.ne(userId))
                        )
                        .orderBy(post.createdDate.desc())
                        .limit(50)
                        .fetch()
        );
    }
}
