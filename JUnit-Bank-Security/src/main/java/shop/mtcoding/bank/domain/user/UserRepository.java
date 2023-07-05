package shop.mtcoding.bank.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	// select * from user where username = ?
	Optional<User> findByUsername(String username); // JPA NameQuery가 작동
	// save -> 이미 만들어져 있음(테스트 불필요)
}
