package com.sparta.springboardteam7.controller;

import com.sparta.springboardteam7.dto.comment.CommentRequestDto;
import com.sparta.springboardteam7.dto.comment.CommentResponseDto;
import com.sparta.springboardteam7.dto.global.PassResponseDto;
import com.sparta.springboardteam7.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 추가
    @PostMapping("/comment/{id}")
    public CommentResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        try {
            return commentService.createComment(id, requestDto, request);
        } catch (Exception e) {
            return new CommentResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);             // 예외 발생시 에러 내용, Httpstatus(400)을 리턴값으로 전달한다.
        }
    }

    // Comment Update
    @PutMapping("/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        try {
            return commentService.updateComment(id, requestDto, request);
        } catch (Exception e) {
            return new CommentResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);           // 예외 발생시 에러 내용, Httpstatus(400)을 리턴값으로 전달한다.
        }
    }

    // Comment Delete
    @DeleteMapping("/comment/{id}")
    public PassResponseDto deleteComment(@PathVariable Long id, HttpServletRequest request) {
        try {
            return commentService.deleteComment(id, request);
        } catch (Exception e) {
            return new CommentResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);            // 예외 발생시 에러 내용, Httpstatus(400)을 리턴값으로 전달한다.
        }
    }
}