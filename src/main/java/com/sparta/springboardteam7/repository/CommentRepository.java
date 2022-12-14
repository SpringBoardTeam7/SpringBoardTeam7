package com.sparta.springboardteam7.repository;

import com.sparta.springboardteam7.entity.Board;
import com.sparta.springboardteam7.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoard(Board board);
}
