package com.example.restfulwebservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration // Spring boot 기동 시 메모리에 설정 정보를 같이 로딩하게 된다
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
		throws Exception {
			auth.inMemoryAuthentication()
				.withUser("minji")
				.password("{noop}test1234") // {noop} : 어떤 동작도 없이 인코딩 없이 바로 사용(no operation)
				.roles("USERS");

	}
}
