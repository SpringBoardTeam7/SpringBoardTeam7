package com.sparta.springboardteam7.dto.comment;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String id;
    private String username;
    private String contents;
}