package com.example.restfulwebservice.user.repository;

import com.example.restfulwebservice.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> { // 어떠한 Entity를 다룰 것인가, 해당 entity의 기본 키 타입

}
