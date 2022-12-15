package com.sparta.springboardteam7.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.springboardteam7.dto.comment.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String username;

    @ManyToOne                                            // Comment(many) <-> Board(one) Join
    @JoinColumn(name = "BOARD_ID", nullable = false)      // Board Primary key값을 가져와서 매핑시킴
    @JsonIgnore                                           // 게시물 조회시 댓글에 blog 컬럼 내용이 보이지않게 해당 데이터는 Ignore 되도록 처리함
    private Board board;


    public Comment(CommentRequestDto requestDto, Board board, String username) {
        this.contents = requestDto.getContents();         // 사용자가 입력한 댓글 내용
        this.board = board;                               // Board 컬럼 데이터
        this.username = username;
    }

    public void update(CommentRequestDto requestDto) {
        this.contents = requestDto.getContents();         // 사용자가 입력한 댓글 내용
    }
}
