package com.example.restfulwebservice.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

	private UserDaoService service;

	// 생성자를 통한 의존성 주입
	public UserController(UserDaoService service) {
		this.service = service;
	}

	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}

	// GET users/10 -> String
	@GetMapping("/users/{id}")
	public User retrieveUser(@PathVariable int id) {
		User user = service.findOne(id);
		if (user == null){
			throw new UserNotFoundException(String.format("ID[%s] is not found", id));
		}
		return user;
	}

	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User savedUser = service.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId())
			.toUri();

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
	public void updateUser(@PathVariable int id){
		User user = service.updateById(id);
		if (user == null){
			throw new UserNotFoundException(String.format("ID[%s] is not found", id));
		}
		if (!user.getIsPass())
			user.setIsPass(true);
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
