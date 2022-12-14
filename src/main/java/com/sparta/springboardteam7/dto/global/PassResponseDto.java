package com.sparta.springboardteam7.dto.global;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PassResponseDto {
    private int statusCode;
    private String msg;

    public PassResponseDto(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
