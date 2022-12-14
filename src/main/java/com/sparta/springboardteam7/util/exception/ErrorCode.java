package com.sparta.springboardteam7.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 400 Bad Request : 잘못된 요청
//    DUPLICATED_BOARD(BAD_REQUEST,  "중복된 게시글이 존재합니다."),
    DUPLICATED_USER(BAD_REQUEST, "중복된 닉네임이 존재합니다."),


    // 401 UNAUTHORIZED : 인증되지 않은 사용자
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다."),
    UNAUTHORIZED_USER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다."),
    INVALID_AUTH_BOARD(UNAUTHORIZED, "해당 게시글에 대한 권한이 없습니다."),
    INVALID_AUTH_COMMENT(UNAUTHORIZED, "해당 댓글에 대한 권한이 없습니다."),


    // 403 Forbidden : 로그인 로직에서 사용
    WRONG_PASSWORD(FORBIDDEN, "잘못된 비밀번호 입니다."),


    // 404 NOT_FOUND :  Resource 를 찾을 수 없음
    NOT_FOUND_BOARD(NOT_FOUND, "존재하지 않는 게시글 입니다."),
    NOT_FOUND_COMMENT(NOT_FOUND, "존재하지 않는 댓글 입니다."),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다."),
    NOT_FOUND_USER(NOT_FOUND, "존재하지 않는 사용자 입니다."),

    // 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다.")

    ;


    private final HttpStatus httpStatus;
    private final String errorMessage;
}
