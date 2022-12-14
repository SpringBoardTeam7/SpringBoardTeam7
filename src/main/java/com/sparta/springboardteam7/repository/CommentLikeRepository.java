package com.sparta.springboardteam7.repository;

import com.sparta.springboardteam7.entity.Comment;
import com.sparta.springboardteam7.entity.CommentLike;
import com.sparta.springboardteam7.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentAndMember(Comment comment, User user);
    CommentLike findByCommentIdAndMemberId(Long commentId, Long memberId);
}