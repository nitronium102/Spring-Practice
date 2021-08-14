package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
	Member save(Member member); // 회원 저장

	// 저장소에서 찾기
	Optional<Member> findById(Long id); // optional로 감싸서 반환
	Optional<Member> findByName(String name);
	List<Member> findAll();
}
