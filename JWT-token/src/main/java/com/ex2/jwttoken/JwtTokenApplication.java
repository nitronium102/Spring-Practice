package com.ex2.jwttoken;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@Configuration
public class JwtTokenApplication {

	@Bean
	public PasswordEncoder passwordEncoder(){
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(JwtTokenApplication.class, args);
	}

}
