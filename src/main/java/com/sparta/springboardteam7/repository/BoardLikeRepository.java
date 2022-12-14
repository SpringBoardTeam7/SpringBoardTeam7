package com.sparta.springboardteam7.repository;

import com.sparta.springboardteam7.entity.Board;
import com.sparta.springboardteam7.entity.BoardLike;
import com.sparta.springboardteam7.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long>  {

    Optional<BoardLike> findByBoardAndMember(Board board, User user);
    BoardLike findByBoardIdAndMemberId(Long boardId, Long userId);
}