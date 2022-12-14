package com.sparta.springboardteam7.service;

import com.sparta.springboardteam7.entity.Board;
import com.sparta.springboardteam7.entity.BoardLike;
import com.sparta.springboardteam7.entity.User;
import com.sparta.springboardteam7.repository.BoardLikeRepository;
import com.sparta.springboardteam7.repository.BoardRepository;
import com.sparta.springboardteam7.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
    public void saveLike(Long boardId, User user) {
        userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new BlogException(BlogExceptionType.MEMBER_NOT_FOUND)
        );
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new BlogException(BlogExceptionType.BOARD_NOT_FOUND)
        );
        Optional<BoardLike> like = boardLikeRepository.findByBoardAndMember(board, user);

        // 이미 ‘좋아요’한 게시글에 다시 요청을 하면 ‘좋아요’를 했던 기록이 취소
        if(like.isPresent()) {
            BoardLike boardLike = boardLikeRepository.findByBoardIdAndMemberId(boardId, user.getId());
            boardLikeRepository.delete(boardLike);
        }
        else {
            BoardLike boardLike = new BoardLike(user, board);
            boardLikeRepository.save(boardLike);
        }
    }
}
