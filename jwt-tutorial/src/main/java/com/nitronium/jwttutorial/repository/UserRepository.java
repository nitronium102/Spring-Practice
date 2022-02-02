package com.nitronium.jwttutorial.repository;

import com.nitronium.jwttutorial.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	@EntityGraph(attributePaths = "authorities") // @EntityGraph -> LAZY가 아니라 EAGER 조회로 권한 정보 가져옴
	// username을 기준으로 authority도 같이 조회
	Optional<User> findOneWithAuthoritiesByUsername(String username);
}