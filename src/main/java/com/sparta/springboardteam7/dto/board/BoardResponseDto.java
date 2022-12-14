package com.sparta.springboardteam7.dto.board;

import com.sparta.springboardteam7.entity.Board;
import com.sparta.springboardteam7.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;

    private Long userId;
    private int like;
    private List<Comment> comment = new ArrayList<>();

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.userId = board.getUser().getUserId();
        this.like = board.getBoardLikes().size();
        this.comment = board.getComments();
    }
}
