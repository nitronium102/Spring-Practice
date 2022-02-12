package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
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

	// GET /adimn/users/1 -> /admin/v1/users/1
	@GetMapping("/v1/users/{id}")
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

	@GetMapping("/v2/users/{id}")
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
