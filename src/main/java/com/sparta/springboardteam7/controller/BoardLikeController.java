package com.sparta.springboardteam7.controller;

import com.sparta.springboardteam7.dto.global.PassResponseDto;
import com.sparta.springboardteam7.service.BoardLikeService;
import com.sparta.springboardteam7.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardLikeController {
    private final BoardLikeService boardLikeService;

    @PostMapping("/boards/{boardId}/like")
    public PassResponseDto saveLike(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl details) {
        boardLikeService.saveLike(boardId, details.getUser());
        return new PassResponseDto(HttpStatus.OK, "success");
    }
}
