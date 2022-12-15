package com.sparta.springboardteam7.controller;

import com.sparta.springboardteam7.dto.global.PassResponseDto;
import com.sparta.springboardteam7.service.CommentLikeService;
import com.sparta.springboardteam7.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/comments/{commentId}/like")
    public PassResponseDto saveCommentLike(@PathVariable Long commentId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentLikeService.saveCommentLike(commentId, userDetails.getUser());
    }
}
