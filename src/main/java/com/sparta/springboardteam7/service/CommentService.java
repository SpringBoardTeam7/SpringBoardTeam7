package com.sparta.springboardteam7.service;

import com.sparta.springboardteam7.dto.comment.CommentRequestDto;
import com.sparta.springboardteam7.dto.comment.CommentResponseDto;
import com.sparta.springboardteam7.dto.global.PassResponseDto;
import com.sparta.springboardteam7.entity.Board;
import com.sparta.springboardteam7.entity.Comment;
import com.sparta.springboardteam7.entity.User;
import com.sparta.springboardteam7.entity.UserRoleEnum;
import com.sparta.springboardteam7.repository.BoardRepository;
import com.sparta.springboardteam7.repository.CommentRepository;
import com.sparta.springboardteam7.repository.UserRepository;
import com.sparta.springboardteam7.util.exception.CustomException;
import com.sparta.springboardteam7.util.exception.ErrorCode;
import com.sparta.springboardteam7.util.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public PassResponseDto createComment(Long id, CommentRequestDto requestDto, User user) {

        // 게시글 존재 여부 확인
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_BOARD)
        );

        String username = user.getUsername();
        commentRepository.saveAndFlush(new Comment(requestDto, board, username));

        return new PassResponseDto(HttpStatus.OK, "댓글 작성 완료");
    }

    @Transactional
    public PassResponseDto updateComment(Long id, CommentRequestDto requestDto, User user) {

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));

        if (comment.getUsername().equals(user.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment.update(requestDto);
            return new PassResponseDto(HttpStatus.OK, "댓글 수정 완료");
        } else {
            throw new CustomException(ErrorCode.INVALID_AUTH_COMMENT);
        }
    }

    @Transactional
    public PassResponseDto deleteComment(Long id, User user) {

        // 댓글 존재여부 확인
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_COMMENT)
        );

        // 댓글 삭제 조건 : 어드민 계정이거나 닉네임이 일치할 시 삭제
        if (user.getRole().equals(UserRoleEnum.ADMIN) || user.getUsername().equals(comment.getUsername())) {
            commentRepository.deleteById(id);
            return new PassResponseDto(HttpStatus.OK, "댓글 삭제 완료");
        } else {
            throw new CustomException(ErrorCode.INVALID_AUTH_COMMENT);
        }
    }
}