package com.sparta.springboardteam7.util.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


// ControllerAdvice 는 여러 컨트로럴에 대해 전역적으로 ExceptionHandler 를 적용
// RestControllerAdvice 는 응답이 JSON 형태로 나온다.

/** ResponseEntityExceptionHandler
 * 예외를 미리 처리해둔 추상 클래스로 스프링 예외에 대한 ExceptionHandler 가 모두 구현되어 있으므로,
 * ControllerAdvice 가 이를 상속받게 해서 사용하면 된다.
 * 하지만 에러 메세지는 반환하지 않으므로 스프링 예외에 대한 에러 응답을 보내기 위해 메소드를 오버라이딩 해야 한다.
 * 스프링에서는 HandlerExceptionResolver 가 모든 Exception 을 가로채서 처리한다.
 * ResponseStatusExceptionResolver 라는 클래스가 ResponseStatusException 또는 @ResponseStatus
 * 어노테이션이 붙은 Exception 을 찾아서 처리해준다.
 *
 * ErrorCode              : 핵심, 모든 예외 케이스를 이곳에서 관리
 * CustomException        : 기본적으로 제공되는 Exception 외에 사용하기 위함
 * ErrorResponse          : 사용자에게 JSON 형식으로 보여주기 위해 에러 응답 형식 지정
 * GlobalExceptionHandler : Custom Exception Handler
 *      어노테이션 ControllerAdvice  : 프로젝트 전역에서 발생하는 Exception 을 잡기 위한 클래스
 *                ExceptionHandler  : 특정 Exception 을 지정해서 별도로 처리해줌
 */
@RestControllerAdvice
public class ExceptionHandlerConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        return ErrorResponse.toResponseEntity(ex.getErrorCode());
    }
}
