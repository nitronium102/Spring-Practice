package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

	private static Map<Long, Member> store = new HashMap<>();
	private static long sequence = 0L; // 시퀀스 값 생성(1, 2, 3...)

	@Override
	public Member save(Member member) {
		member.setId(++sequence);
		store.put(member.getId(), member);
		return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
		return Optional.ofNullable(store.get(id)); // null값 나올 수 있는 경우 Optional.ofNullable로 감싸기
	}

	@Override
	public Optional<Member> findByName(String name) {
		return store.values().stream()
			.filter(member -> member.getName().equals(name))
			.findAny(); // 하나라도 찾기(Optional로 반환
	}

	@Override
	public List<Member> findAll() {
		return new ArrayList<>(store.values());
	}

	public void clearStore(){
		store.clear();
	}
}
