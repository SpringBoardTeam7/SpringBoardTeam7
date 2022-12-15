package com.sparta.springboardteam7.service;

import com.sparta.springboardteam7.dto.global.PassResponseDto;
import com.sparta.springboardteam7.entity.Board;
import com.sparta.springboardteam7.entity.BoardLike;
import com.sparta.springboardteam7.entity.User;
import com.sparta.springboardteam7.repository.BoardLikeRepository;
import com.sparta.springboardteam7.repository.BoardRepository;
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
public class BoardLikeService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;

    @Transactional
    public PassResponseDto saveLike(Long boardId, User user) {
        userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER)
        );
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_BOARD)
        );
        Optional<BoardLike> like = boardLikeRepository.findByBoardAndUser(board, user);

        // 이미 ‘좋아요’한 게시글에 다시 요청을 하면 ‘좋아요’를 했던 기록이 취소
        if(like.isPresent()) {
            BoardLike boardLike = boardLikeRepository.findByBoardIdAndUserId(boardId, user.getId());
            boardLikeRepository.delete(boardLike);
            return new PassResponseDto(HttpStatus.OK, "좋아요 완료");
        }
        else {
            BoardLike boardLike = new BoardLike(user, board);
            boardLikeRepository.save(boardLike);
            return new PassResponseDto(HttpStatus.OK, "좋아요 취소");
        }
    }
}
