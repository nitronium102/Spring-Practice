package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin") // 모든 api가 공통적으로 가지고 있는 이름을 클래스 블록에 선언
public class AdminController {

	private UserDaoService service;

	public AdminController(UserDaoService service) {
		this.service = service;
	}

	@GetMapping("/users")
	public MappingJacksonValue retrieveAllUsers() {
		List<User> users = service.findAll();

		// bean의 property를 제어
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
			.filterOutAllExcept("id", "name", "joinDate", "password"); // 포함시키고 싶은 필터값 선언

		// User에 @JsonFilter("UserInfo") 지정
		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(users); // 유저 데이터 전달
		mapping.setFilters(filters); // 필터 적용

		return mapping;
	}

	@GetMapping("/users/{id}")
	// setFilters나 setSerialView를 사용 시 꼭 MappingJacksonValue 타입으로 감싸서 반환
	public MappingJacksonValue retrieveUser(@PathVariable int id) { // 관리자 : 유저의 정보를 모두 가져올 수 있도록!
		User user = service.findOne(id);
		if (user == null){
			throw new UserNotFoundException(String.format("ID[%s] is not found", id));
		}

		// bean의 property를 제어
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
			.filterOutAllExcept("id", "name", "joinDate", "ssn"); // 포함시키고 싶은 필터값 선언

		// User에 @JsonFilter("UserInfo") 지정
		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(user); // 유저 데이터 전달
		mapping.setFilters(filters); // 필터 적용

		return mapping;
	}
}
