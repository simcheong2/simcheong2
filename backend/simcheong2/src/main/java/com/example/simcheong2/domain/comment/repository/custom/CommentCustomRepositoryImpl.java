package com.example.simcheong2.domain.comment.repository.custom;

import com.example.simcheong2.domain.comment.entity.Comment;
import com.example.simcheong2.domain.post.repository.custom.PostCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import static com.example.simcheong2.domain.comment.entity.QComment.comment;

@Slf4j
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<List<Comment>> searchCommentByPostId(Integer postId){
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(comment)
                        .where(comment.post.postId.eq(postId))
                        .orderBy(comment.post.createdDate.desc())
                        .fetch()
        );
    };

}
