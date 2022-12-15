package com.sparta.springboardteam7.dto.global;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class PassResponseDto {
    private HttpStatus statusCode;
    private String msg;
    public PassResponseDto(HttpStatus statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
