package com.sparta.springboardteam7.dto.comment;

import com.sparta.springboardteam7.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public CommentDto(Comment comment){
        this.id = comment.getId();                  // 댓글 Id
        this.contents = comment.getContents();      // 댓글 내용
        this.createdAt = comment.getCreatedAt();    // 댓글 생성시간
        this.modifiedAt = comment.getModifiedAt();  // 댓글 수정시간
    }
}