package com.example.memberManage.repository;

import com.example.memberManage.domain.Member;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @Test
    public void save() {
        // given
        Member member = new Member();
        member.setName("spring");

        // when
        repository.save(member);

        // then
        Member result = repository.findById(member.getId()).get(); // optional에서 get으로 꺼낼 수 있다
        //System.out.println("result = " + (result == member));
        //Assertions.assertEquals(member, result); // (jupiter)expected, actual -> 통과하면 출력 X
        assertThat(result).isEqualTo(member); // (assertj)result가 member랑 같니?
    }

    @Test
    public void findByName() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // when
        Member result = repository.findByName("spring2").get();

        // then
        assertThat(result).isEqualTo(member1);
    }
}
