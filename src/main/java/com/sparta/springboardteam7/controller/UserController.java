package com.sparta.springboardteam7.controller;

import com.sparta.springboardteam7.dto.user.LoginRequestDto;
import com.sparta.springboardteam7.dto.global.PassResponseDto;
import com.sparta.springboardteam7.dto.user.SignupRequestDto;
import com.sparta.springboardteam7.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

/*    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }*/

/*    @GetMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    @GetMapping("/signup")
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }*/

    @PostMapping("/signup")
    public PassResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);

        // builder 패턴
        // 순서 상관없이 사용이 가능하다.
//        return ResponseEntity.ok(MsgResponseDto.builder()
//                .statusCode(HttpStatus.OK.value())
//                .msg("회원가입 완료")
//                .build());

        return new PassResponseDto(HttpStatus.OK.value(), "회원 가입 완료");
    }

//    @PostMapping("/login")
//    public ResponseEntity<MsgResponseDto> login(@RequestBody LoginRequestDto loginRequestDto,
//                                              HttpServletResponse response) {
//
//        userService.login(loginRequestDto, response);
//
//        return ResponseEntity.ok(new MsgResponseDto("로그인 성공", HttpStatus.OK.value()));
//    }


    @PostMapping("/login")
    public PassResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        //클라이언트에 반환하기 위해 response 객체
        userService.login(loginRequestDto, response);

        return new PassResponseDto(HttpStatus.OK.value(), "로그인 완료");
    }


    @PostMapping("forbidden")
    public ModelAndView forbidden() {
        return new ModelAndView("forbidden");
    }
}
