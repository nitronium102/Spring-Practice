package com.example.restfulwebservice.user.controller;

import com.example.restfulwebservice.exception.UserNotFoundException;
import com.example.restfulwebservice.user.repository.UserRepository;
import com.example.restfulwebservice.user.domain.Post;
import com.example.restfulwebservice.user.domain.User;
import com.example.restfulwebservice.user.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	// http://localhos:8088/jpa/users
	@GetMapping("/users")
	public List<User> retreiveAllUsers(){
		return userRepository.findAll();
	}

	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id){
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent())
			throw new UserNotFoundException(String.format("ID[%s] not found", id));

		// HATEOAS 사용
		EntityModel<User> entityModel = EntityModel.of(user.get());
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retreiveAllUsers());
		entityModel.add(linkTo.withRel("all-users"));

		return entityModel;
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id){
		userRepository.deleteById(id);
	}

	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user){
		User savedUser = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId())
			.toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/users/{id}/posts")
	public List<Post> retrieveAllPostsByUser(@PathVariable int id){
		Optional<User> user = userRepository.findById(id);

		if (!user.isPresent())
			throw new UserNotFoundException(String.format("ID[%s] not found", id));

		return user.get().getPosts();
	}

	@PostMapping("/users/{id}/posts")
	public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post){
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent())
			throw new UserNotFoundException(String.format("ID[%s] not found", id));

		post.setUser(user.get()); // .get() : optional 값이 존재하면 해당 값을 반환, 없으면 null 반환
		Post savedPost = postRepository.save(post);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedPost.getId())
			.toUri();

		return ResponseEntity.created(location).build();
	}
}
