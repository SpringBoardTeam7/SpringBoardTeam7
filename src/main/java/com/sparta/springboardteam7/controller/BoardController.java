package com.sparta.springboardteam7.controller;

import com.sparta.springboardteam7.dto.board.BoardRequestDto;
import com.sparta.springboardteam7.dto.board.BoardResponseDto;
import com.sparta.springboardteam7.dto.global.PassResponseDto;
import com.sparta.springboardteam7.service.BoardService;
import com.sparta.springboardteam7.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/boards/new")
    public BoardResponseDto saveBoard(@RequestBody @Valid BoardRequestDto requestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.saveBoard(requestDto, userDetails.getUser());
    }

    @PutMapping("/boards/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id,
                                        @RequestBody @Valid BoardRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updateBoard(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/boards/{boardId}")
    public PassResponseDto deleteBoard(@PathVariable Long boardId,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.deleteBoard(boardId, userDetails.getUser());
        return new PassResponseDto(HttpStatus.OK.value(), "success");
    }

    @GetMapping("/boards")
    public Page<BoardResponseDto> getBoards(Pageable pageable) {
        return boardService.getBoards(pageable);
    }

    @GetMapping("/boards/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id) {
        return boardService.findOne(id);
    }
}
