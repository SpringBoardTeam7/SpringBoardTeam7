package com.sparta.springboardteam7.controller;

import com.sparta.springboardteam7.dto.comment.CommentRequestDto;
import com.sparta.springboardteam7.dto.comment.CommentResponseDto;
import com.sparta.springboardteam7.dto.global.PassResponseDto;
import com.sparta.springboardteam7.service.CommentService;
import com.sparta.springboardteam7.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 추가
    @PostMapping("/comment/{id}")
    public PassResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(id, requestDto, userDetails.getUser());
//        try {
//            return commentService.createComment(id, requestDto, request);
//        } catch (Exception e) {
//            return new CommentResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);             // 예외 발생시 에러 내용, Httpstatus(400)을 리턴값으로 전달한다.
//        }
    }

    // Comment Update
    @PutMapping("/comment/{id}")
    public PassResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id, requestDto, userDetails.getUser());
    }

    // Comment Delete
    @DeleteMapping("/comment/{id}")
    public PassResponseDto deleteComment(@PathVariable Long id,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id, userDetails.getUser());
    }
}