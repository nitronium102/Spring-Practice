package com.rest.restfulwebservice.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// 비즈니스 로직 -> 서비스
@Service // 명확한 의존성 주입을 위해 얘가 뭔지 표시해준다
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int usersCount = 3; // id 할당

    static{
        users.add(new User(1, "Nitro", new Date()));
        users.add(new User(2, "Ash", new Date()));
        users.add(new User(3, "Nithke", new Date()));
    }

    public List<User> findAll(){ // 모든 유저 호출
        return users;
    }

    public User save(User user) { // 사용자 추가
        if (user.getId() == null){
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    public User findOne(int id){ // 특정 유저 반환환
       for (User user : users){
            if (user.getId() == id){
                return user;
            }
        }
       return null; // 유저가 없을 경우
    }
}
