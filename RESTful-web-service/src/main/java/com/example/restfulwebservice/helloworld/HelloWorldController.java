package com.example.restfulwebservice.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	// GET
	// /hello-world (endpoint)
	@GetMapping("/hello-world")
	public String helloWorld() {
		return "Hello world";
	}

	@GetMapping("/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello world bean"); // json 형식으로 반환
	}

	@GetMapping("/hello-world-bean/path-variable/{name}")
	public HelloWorldBean helloWorldBean(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s", name)); // json 형식으로 반환
	}

}
