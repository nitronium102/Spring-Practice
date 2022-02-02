package com.example.restfulwebservice.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController // @Controller + @RequestBody : view를 갖지 않는 REST Data(JSON/XML) 반환 -> 자동으로 ResponseBody에 넣어준다
public class HelloWorldController {

	@Autowired // annotation을 통한 의존성 주입 : spring framework에 등록되어 있는 bean 중 같은 타입을 가진 bean을 자동으로 주입
	private MessageSource messageSource;

	// GET /hello-world (endpoint)
	@GetMapping("/hello-world")
	public String helloWorld() {
		return "Hello world";
	}

	@GetMapping("/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello world bean"); // json 형식으로 반환
	}

	@GetMapping("/hello-world-bean/path-variable/{name}")
	public HelloWorldBean helloWorldBean(@PathVariable String name) { // @PathVariable : 이 변수가 가변 데이터임을 명시
		return new HelloWorldBean(String.format("Hello World, %s", name)); // json 형식으로 반환
	}

	@GetMapping(path="/hello-world-internationalized")
	public String helloWorldInternationalized(
		@RequestHeader(name="Accept-Language", required = false) Locale locale) {
		// "Accept-Language" 값이 설정되지 않았을 때는 자동으로 default값인 한국어로 설정(기본 locale 값)
		// "Accept-Language" 가 설정되어 있으면 locale에 저장

		// 첫번째 인자: 설정 파일에서의 key 값
		// 두번째 인자: 만약 parameter를 가진 가변변수라면 parameter 지정
		// 세번째 인자: RequestHeader를 통해 전달받은 locale
		return messageSource.getMessage("greeting.message", null, locale);
	}

}
