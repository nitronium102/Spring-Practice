package com.example.memberManage.repository;

import com.example.memberManage.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>(); // key가 Long, value는 Member
    private static long sequence = 0L; // 키 값 생성해준다

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;

    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // null이어도 가능
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() // 람다식(루프로 돌린다)
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() { // Map인데 반환은 List
        return new ArrayList<>(store.values()); // members 반환
    }
}
