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

        commentRepository.saveAndFlush(new Comment(requestDto, board));

        return new PassResponseDto(HttpStatus.OK, "댓글 작성 완료");
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);           // Request(Token) -> BoardService
        Claims claims;

        if (token != null) {                                      // 토큰이 있는 경우에만 댓글 작성 가능
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);

                User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                        () -> new CustomException(ErrorCode.NOT_FOUND_USER)
                );

                UserRoleEnum userRoleEnum = user.getRole();
                Comment comment;

                if (userRoleEnum == UserRoleEnum.USER) {
                    comment = commentRepository.findById(id).orElseThrow(
                            () -> new CustomException(ErrorCode.NOT_FOUND_USER)
                    );

                    String loginUserName = user.getUsername();
                    if (!comment.getUsername().equals(loginUserName) || user.getRole().equals(UserRoleEnum.ADMIN)) {
                        throw new CustomException(ErrorCode.INVALID_AUTH_COMMENT);
                    }
                } else {
                    comment = commentRepository.findById(id).orElseThrow(
                            () -> new CustomException(ErrorCode.NOT_FOUND_COMMENT)
                    );
                }

                comment.update(requestDto);                                                 // 입력받은 내용 Update 처리
                Comment saveComment = commentRepository.save(comment);                      // 업데이트한 내용 저장 (Transection 어노테이션을 제거하여 변경된 상태에서 커밋해주는 로직이 필요함)
                return new CommentResponseDto("success", HttpStatus.OK, saveComment);
            }
        } else {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }
        return null;
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