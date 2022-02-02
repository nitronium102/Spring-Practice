package com.nitronium.jwttutorial.service;


import java.util.Collections;

import com.nitronium.jwttutorial.dto.UserDto;
import com.nitronium.jwttutorial.entity.Authority;
import com.nitronium.jwttutorial.entity.User;
import com.nitronium.jwttutorial.exception.DuplicateMemberException;
import com.nitronium.jwttutorial.repository.UserRepository;
import com.nitronium.jwttutorial.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public UserDto signup(UserDto userDto) {
		if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
			throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
		}

		Authority authority = Authority.builder()
			.authorityName("ROLE_USER") // 궈한이 다름!
			.build();

		User user = User.builder()
			.username(userDto.getUsername())
			.password(passwordEncoder.encode(userDto.getPassword()))
			.nickname(userDto.getNickname())
			.authorities(Collections.singleton(authority))
			.activated(true)
			.build();

		return UserDto.from(userRepository.save(user));
	}

	@Transactional(readOnly = true)
	public UserDto getUserWithAuthorities(String username) {
		return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
	}

	@Transactional(readOnly = true)
	public UserDto getMyUserWithAuthorities() { // 현재 SecurityContext에 저장된 username의 정보만 가져옴
		return UserDto.from(SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername).orElse(null));
	}
}