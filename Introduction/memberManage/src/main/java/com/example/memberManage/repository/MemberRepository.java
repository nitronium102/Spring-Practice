package com.example.memberManage.repository;

import com.example.memberManage.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); // 저장소에 저장
    Optional<Member> findById(Long id); // null 있을 경우 optional로 감싼다
    Optional<Member> findByName(String name);
    List<Member> findAll(); // 지금까지 저장된 모든 회원 리스트를 반환

}
