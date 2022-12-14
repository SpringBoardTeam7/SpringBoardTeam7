package com.sparta.springboardteam7.service;

import com.sparta.springboardteam7.dto.*;
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
    public CommentResponseDto createComment(Long id, CommentRequestDto requestDto, HttpServletRequest request) {

        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰 검사
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 요청받은 DTO 로 DB에 저장할 객체 만들기

            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new NullPointerException("해당 게시글은 존재하지 않습니다.")
            );

            Comment comment = commentRepository.saveAndFlush(new Comment(requestDto, board));

            return new CommentResponseDto("success", HttpStatus.OK, comment);
        } else {
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }
    }

    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);           // Request(Token) -> BoardService
        Claims claims;

        if (token != null) {                                      // 토큰이 있는 경우에만 댓글 작성 가능
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);

                User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                        () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
                );

                UserRoleEnum userRoleEnum = user.getRole();
                Comment comment;

                if (userRoleEnum == UserRoleEnum.USER) {
                    comment = commentRepository.findById(id).orElseThrow(
                            () -> new NullPointerException("해당 댓글은 존재하지 않습니다.")
                    );

                    String loginUserName = user.getUsername();
                    if (!comment.getUsername().equals(loginUserName)) {
                        throw new IllegalArgumentException("회원님의 댓글이 아니므로 업데이트 할 수 없습니다.");
                    }
                } else {
                    comment = commentRepository.findById(id).orElseThrow(
                            () -> new NullPointerException("해당 댓글은 존재하지 않습니다.")
                    );
                    comment.update(requestDto);                                             // 입력받은 내용 Update 처리
                }
                Comment saveComment = commentRepository.save(comment);                      // 업데이트한 내용 저장 (Transection 어노테이션을 제거하여 변경된 상태에서 커밋해주는 로직이 필요함)
                return new CommentResponseDto("success", HttpStatus.OK, saveComment);
            }
        } else {
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
        }
        return null;
    }

    public PassResponseDto deleteComment(Long id, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {                                         // Request(Token) -> BoardService
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            UserRoleEnum userRoleEnum = user.getRole();
            Comment comment;

            if (userRoleEnum == UserRoleEnum.USER) {
                comment = commentRepository.findById(id).orElseThrow(
                        () -> new NullPointerException("해당 댓글은 존재하지 않습니다.")
                );

                String loginUserName = user.getUsername();
                if (!comment.getUsername().equals(loginUserName)) {
                    throw new IllegalArgumentException("회원님의 댓글이 아니므로 삭제할 수 없습니다.");
                }
            } else {
                comment = commentRepository.findById(id).orElseThrow(
                        () -> new NullPointerException("해당 댓글은 존재하지 않습니다.")
                );
                commentRepository.deleteById(id);                                          // 입력받은 게시판 id 삭제 처리
            }
            return new PassResponseDto(HttpStatus.OK, "success");
        } else {
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
        }
    }

}