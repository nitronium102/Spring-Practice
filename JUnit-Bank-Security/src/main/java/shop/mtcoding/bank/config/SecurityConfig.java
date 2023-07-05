package shop.mtcoding.bank.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import shop.mtcoding.bank.domain.user.UserEnum;

@Configuration // Ioc에 설정파일로 bean 등록
public class SecurityConfig {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Bean // Ioc 컨테이너에 BCryptPassWordEncoder() 객체가 등록됨 (@Configuration 붙은 클래스 아래의 @Bean만 등록됨)
	public BCryptPasswordEncoder passwordEncoder() {
		log.debug("디버그 : BCryptPasswordEncoder 빈 등록됨");
		return new BCryptPasswordEncoder();
	}

	// JWT 필터 등록이 필요함

	// JWT 서버를 만들 예정(SESSION 사용 안 함)
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		log.debug("디버그 : filterChain 빈 등록됨");
		http.headers().frameOptions().disable(); // iframe 허용 안 함
		http.csrf().disable(); // enable이면 post 작동 안 함
		http.cors().configurationSource(configurationSource()); // cors : javascript 요청을 거부

		/// jSessionId를 서버 쪽에서 관리하지 않겠다
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// react, 앱으로 요청하기 때문에 자체적으로 생성되는 form login 사용 안 함
		http.formLogin().disable();
		// httpBasic은 브라우저가 팝업창을 이용해서 사용자 인증을 진행
		http.httpBasic().disable();

		// Exception 가로채기 (원래는 스프링에서 exception 발생하면 가로챔)
		http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
			// response.setContentType("application/json; charset=utf-8");
			response.setStatus(403);
			response.getWriter().println("error");
		});

		http.authorizeRequests()
			.antMatchers("/api/s/**").authenticated()
			.antMatchers("/api/admin/**").hasRole("" + UserEnum.ADMIN) // 최근 공식문서에서는 ROLE_ 안 붙여도 됨
			.anyRequest().permitAll();

		return http.build();
	}

	public CorsConfigurationSource configurationSource() {
		log.debug("디버그 : configurationSource core 설정이 securityFilterChain에 등록됨");
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (javascript 요청 허용)
		configuration.addAllowedOriginPattern(
			"*"); // 모든 IP 주소 허용 (프론트엔드 IP, react만 허용) 핸드폰은 js 요청을 하지 않고 java나 swift 쓰기 때문에 cors에 안 걸림
		configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration); // 모든 주소 요청에 대해 해당 설정 적용

		return source;
	}
}
