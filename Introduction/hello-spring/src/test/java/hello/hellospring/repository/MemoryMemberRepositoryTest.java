package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MemoryMemberRepositoryTest {

	MemoryMemberRepository repository = new MemoryMemberRepository();

	// 테스트는 서로 의존관계 없이 독립적으로 실행되어야 한다
	// 테스트 끝날 때마다(afterEach) repository (공유) 데이터 지우기
	@AfterEach
	public void afterEach(){
		repository.clearStore();
	}

	@Test
	public void save(){
		Member member = new Member();
		member.setName("spring");

		repository.save(member);
		Member result = repository.findById(member.getId()).get(); // optional로 감싸져 있어서 get으로 꺼냄
		//Assertions.assertEquals(result, member);
		// assertThat에서 member가 result와 동일한가?
		assertThat(member).isEqualTo(result);
	}

	@Test
	public void findByName(){
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);

		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);

		Member result = repository.findByName("spring1").get();
		assertThat(result).isEqualTo(member1);
	}

	@Test
	public void findAll(){
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);

		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);

		List<Member> result = repository.findAll();

		assertThat(result.size()).isEqualTo(2);
	}
}
