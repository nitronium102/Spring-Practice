package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

// ctrl+shift+T -> 새로운 테스트 클래스 생성
public class MemberService {
	// test와 service에서 서로 다른 repository를 사용함
	// private final MemberRepository memberRepository = new MemoryMemberRepository();
	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository){
		this.memberRepository = memberRepository;
	}

	// 회원 가입
	public Long join(Member member){
		// 같은 이름이 있는 중복 회원은 안 된다.
		validateDuplicateMember(member);
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		memberRepository.findByName(member.getName())
			.ifPresent(m -> {
				throw new IllegalStateException("이미 존재하는 회원입니다.");
			});
	}

	// 전체 회원 조회
	// 서비스는 비즈니스 로직에 맞추어 네이밍
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}

	public Optional<Member> findOne(Long memberId){
		return memberRepository.findById(memberId);
	}
}
