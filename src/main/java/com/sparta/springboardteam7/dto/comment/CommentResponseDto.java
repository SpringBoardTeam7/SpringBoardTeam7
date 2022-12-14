package com.sparta.springboardteam7.dto.comment;

import com.sparta.springboardteam7.dto.global.PassResponseDto;
import com.sparta.springboardteam7.entity.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@Configuration
public class CommentResponseDto extends PassResponseDto { // 응답용 Dto 생성

    CommentDto commentOne; // CommentToDto 연결

    // 생성자(mgs+StatusCode)
    public CommentResponseDto(String msg, HttpStatus statusCode) {
        super(statusCode, msg);
    }

    // 생성자(mgs+StatusCode+Entity)
    public CommentResponseDto(String msg, HttpStatus statusCode, Comment comment){
        super(statusCode, msg);
        this.commentOne = new CommentDto(comment);
    }
}