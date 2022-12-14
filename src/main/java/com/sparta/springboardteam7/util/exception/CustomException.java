package com.sparta.springboardteam7.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
//    public GlobalMsg(String msg, int statusCode) {
//        this.msg = msg;
//        this.statusCode = statusCode;
//    }
}
