package com.example.restfulwebservice.user.controller;

import com.example.restfulwebservice.user.service.UserDaoService;
import com.example.restfulwebservice.exception.UserNotFoundException;
import com.example.restfulwebservice.user.domain.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

// static method를 import하면 해당하는 메소드를 쓰기 위해 긴 클래스명을 사용하지 않아도 된다!
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

	private UserDaoService service;

	// 의존성 주입 : setter 메소드, 생성자의 매개변수를 통해 가능 -> @Autowired를 사용하면 더 간단함
	// DEBUG 레벨의 log에서 userController 검색 -> 'userController' via constructor to bean named 'userDaoService'(의존성 주입 확인)
	public UserController(UserDaoService service) {
		this.service = service;
	}

	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}

	// GET users/10 -> String
	// 기본은 String이지만 매개변수로 int 받으면 자동 변경
	@GetMapping("/users/{id}")
	public ResponseEntity<EntityModel<User>> retrieveUser(@PathVariable int id) {
		User user = service.findOne(id);
		if (user == null){
			// 직접 상황에 맞는 예외 클래스 작성
			throw new UserNotFoundException(String.format("ID[%s] is not found", id));
		}

		// HATEOAS
		EntityModel<User> entityModel = EntityModel.of(user); // 매개변수에 유저 객체값 넣음
		// 유저 객체를 반환시킬 때 유저가 사용할 수 있는 추가적인 정보(링크)를 하이퍼미디어 형식으로 넣어놓기
		// methodOn(연결시킬 메소드)
		// -> this.getClass()가 가지고 있는 데이터 중에서 retrieveAllUsers() 메소드를 연동
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

		// retrieveAllUsers() 메소드를 "all-users"로 href 연결
		entityModel.add(linkTo.withRel("all-users")); // resource 객체에 링크 추가

		return ResponseEntity.ok(entityModel);
	}

	@PostMapping("/users")
	// JDK에 포함된 API와 hibernate library에 포함된 validation 기능
	// @Valid : 사용자 추가할 때 validation 검증
	// @RequestBody : 현재 variable이 requestBody 형식으로 들어옴을 알림
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);

		// ServletUriComponentBuilder -> 현재 요청의 URI를 얻을 수 있다.
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId())
			.toUri();

		// ResponseEntity -> HTTP 상태 코드와 전송하고 싶은 데이터를 전송
		// HTTP 201 Created : 요청이 성공적이었으며 그 결과로 새로운 리소스가 생성됨
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id){
		User user = service.deleteById(id);
		if (user == null){
			throw new UserNotFoundException(String.format("ID[%s] is not found", id));
		}
	}

	@PutMapping("/users/{id}")
	public void updateUser(@PathVariable int id, @RequestBody User user){
		User updatedUser = service.updateById(id, user);
		if (updatedUser == null) {
			throw new UserNotFoundException(String.format("ID[%s] is not found", user.getId()));
		}
	}

	/*@PutMapping("/users/{id}")
	public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable int id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent())
			return ResponseEntity.notFound().build();
		user.setId(id);
		userRepository.save(user);
		return ResponseEntity.noContent().build();
	}*/
}
