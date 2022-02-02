package com.nitronium.jwttutorial.config;

import com.nitronium.jwttutorial.jwt.JwtAccessDeniedHandler;
import com.nitronium.jwttutorial.jwt.JwtAuthenticationEntryPoint;
import com.nitronium.jwttutorial.jwt.JwtSecurityConfig;
import com.nitronium.jwttutorial.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity // 기본적인 웹 보안을 활성화
@EnableGlobalMethodSecurity(prePostEnabled = true) // @PreAuthorize annotation을 메소드 단위로 추가
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final TokenProvider tokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	public SecurityConfig( // 주입
		TokenProvider tokenProvider,
		JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
		JwtAccessDeniedHandler jwtAccessDeniedHandler
	) {
		this.tokenProvider = tokenProvider;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
			.antMatchers(
				"/h2-console/**"
				,"/favicon.ico"
				,"/error"
			);
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			// token을 사용하는 방식이기 때문에 csrf를 disable합니다.
			.csrf().disable()

			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)

			// enable h2-console
			.and()
			.headers()
			.frameOptions()
			.sameOrigin()

			// 세션을 사용하지 않기 때문에 STATELESS로 설정
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			// token이 없는 상태에서 들어오는 요청은 open
			.and()
			.authorizeRequests() // http servlet request를 사용하는 요청들에 대한 접근 제한을 설정
			.antMatchers("/api/hello").permitAll()  // /api/hello에 대한 요청은 인증없이 접근을 허용
			.antMatchers("/api/authenticate").permitAll()
			.antMatchers("/api/signup").permitAll()

			.anyRequest().authenticated() // 나머지 요청들은 모두 인증되어야 한다.

			.and()
			.apply(new JwtSecurityConfig(tokenProvider));
	}
}
