package shop.mtcoding.bank.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.handler.ex.CustomApiException;

@RequiredArgsConstructor
@Service
public class UserService {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	// 서비스는 DTO를 요청받고, DTO로 응답한다
	@Transactional // 메서드 시작할 때 시작되고, 종료될 때 함께 종료
	public JoinResDto 회원가입(JoinReqDto joinReqDto) {
		// 1. 동일 username 여부 검사
		Optional<User> userOP = userRepository.findByUsername(joinReqDto.getUsername());

		if (userOP.isPresent()) {
			// 유저 네임 중복되었다는 뜻
			throw new CustomApiException("동일한 username이 존재합니다.");
		}
		// 2. password encoding
		User userPS = userRepository.save(joinReqDto.toEntity(passwordEncoder));

		// 3. dto 응답
		return new JoinResDto(userPS);
	}

	@Setter
	@Getter
	public static class JoinResDto {
		private Long id;
		private String username;
		private String fullname;

		public JoinResDto(User user) {
			this.id = user.getId();
			this.username = user.getUsername();
			this.fullname = user.getFullname();
		}
	}

	@Getter
	@Setter
	public static class JoinReqDto {
		// 유효성 검사
		private String username;
		private String password;
		private String email;
		private String fullname;

		public User toEntity(BCryptPasswordEncoder passwordEncoder) {
			return User.builder()
				.username(username)
				.password(passwordEncoder.encode(password))
				.email(email)
				.fullname(fullname)
				.role(UserEnum.CUSTOMER)
				.build();
		}
	}
}
