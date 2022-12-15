package com.sparta.springboardteam7.service;

import com.sparta.springboardteam7.dto.global.PassResponseDto;
import com.sparta.springboardteam7.entity.Comment;
import com.sparta.springboardteam7.entity.CommentLike;
import com.sparta.springboardteam7.entity.User;
import com.sparta.springboardteam7.repository.CommentLikeRepository;
import com.sparta.springboardteam7.repository.CommentRepository;
import com.sparta.springboardteam7.repository.UserRepository;
import com.sparta.springboardteam7.util.exception.CustomException;
import com.sparta.springboardteam7.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public PassResponseDto saveCommentLike(Long commentId, User user) {
        userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_COMMENT)
        );
        Optional<CommentLike> like = commentLikeRepository.findByCommentAndUser(comment, user);

        if(like.isPresent()) {
            commentLikeRepository.delete(like.get());
            return new PassResponseDto(HttpStatus.OK, "좋아요 취소");
        }
        else {
            CommentLike commentLike = new CommentLike(user, comment.getBoard(), comment);
            commentLikeRepository.save(commentLike);
            return new PassResponseDto(HttpStatus.OK, "좋아요 완료");
        }
    }
}
