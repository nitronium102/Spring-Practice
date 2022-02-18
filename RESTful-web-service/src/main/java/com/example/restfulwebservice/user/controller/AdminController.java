package com.example.restfulwebservice.user.controller;

import com.example.restfulwebservice.user.domain.User;
import com.example.restfulwebservice.user.service.UserDaoService;
import com.example.restfulwebservice.exception.UserNotFoundException;
import com.example.restfulwebservice.user.UserV2;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

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

	// 제공하고자 하는 MIME 타입 지정
	// 기존에 없던 새로운 값 -> application
	// 버전 이름 : appv1
	// 전달 시키조자 하는 타입 : json
	@GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
	// setFilters나 setSerialView를 사용 시 꼭 MappingJacksonValue 타입으로 감싸서 반환
	public MappingJacksonValue retrieveUserV1(@PathVariable int id) { // 관리자 : 유저의 정보를 모두 가져올 수 있도록!
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

	@GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
	public MappingJacksonValue retrieveUserV2(@PathVariable int id) {
		User user = service.findOne(id);

		if (user == null){
			throw new UserNotFoundException(String.format("ID[%s] is not found", id));
		}

		// 반환받은 User를 UserV2로 변환
		UserV2 userV2 = new UserV2();
		// 두 인스턴스 간에 공통된 필드가 있을 경우 해당 값을 copy함
		BeanUtils.copyProperties(user, userV2); // id, name, joinDate, password, ssn
		userV2.setGrade("VIP");

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
			.filterOutAllExcept("id", "name", "joinDate", "grade");

		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(userV2);
		mapping.setFilters(filters);

		return mapping;
	}
}
