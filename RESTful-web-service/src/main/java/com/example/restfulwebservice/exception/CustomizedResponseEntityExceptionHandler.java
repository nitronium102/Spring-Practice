package com.example.restfulwebservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice // 모든 @Controller가 실행되기 전에 먼저 실행됨 -> 전역 발생 예외 처리 가능
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse =
			new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		// ResponseEntity : http status code와 전송하고자 하는 데이터를 함께 전송
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse =
			new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@Override // 메소드 구현이 잘못되는 것을 방지
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, // 발생한 exception
																  HttpHeaders headers, // request header
																  HttpStatus status,
																  WebRequest request) { // 요청 request
		// ex.getMessage()가 중복되고 너무 길기 때문에 "Validation Failed"로 대체
		// ex.getBindingResut.toString() -> @Size(message="{메시지}") 값도 포함되어 나옴
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
			"Validation Failed",
			ex.getBindingResult().toString());
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

}
