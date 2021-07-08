package com.rest.restfulwebservice.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service){
        this.service = service;
    }

    // 사용자 전체 목록 조회
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    // 사용자 개별 조회
    // GET /users/1 or /users/10 -> 서버측(Controller)에는 String으로 전달된다 / 앞에 int라고 하면 Int형으로 전달
    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){
        return service.findOne(id);
    }

    // 사용자 추가
    @PostMapping("/users")
    public void createUser(@RequestBody User user){ // @ReqBody : Object 형식을 받아올 때 필요
        User savedUser = service.save(user);
    }
}
