package com.example.restfulwebservice.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// HTTP Status Code
// 2XX -> OK
// 4XX -> Client
// 5XX -> Server
@ResponseStatus(HttpStatus.NOT_FOUND) // 응답 상태 코드 값 지정
// CustomizedResponseEntityExceptionHandler.class에서 UserNotFoundException이 발생했을 때, Exception Response를 반환(Http status code도 반환)
// => ResponseStatus 생략 가능
public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String message) {
		super(message); // ex.getMessage()
	}
}
