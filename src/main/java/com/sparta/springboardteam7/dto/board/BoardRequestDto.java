package com.sparta.springboardteam7.dto.board;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private Long id;
    private String title;
    private String content;
}
