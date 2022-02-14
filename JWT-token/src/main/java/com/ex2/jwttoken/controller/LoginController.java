package com.ex2.jwttoken.controller;

import com.ex2.jwttoken.config.JwtTokenProvider;
import com.ex2.jwttoken.domain.Admin;
import com.ex2.jwttoken.dto.LoginReqDto;
import com.ex2.jwttoken.exception.NoSuchAdminException;
import com.ex2.jwttoken.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LoginController {
    private final LoginService service;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public Long signUp(@RequestBody LoginReqDto loginReqDto) {
        return service.joinUser(loginReqDto);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginReqDto loginReqDto) {
        Optional<Admin> optionalAdmin = service.findUser(loginReqDto);
        if(optionalAdmin.isEmpty()) throw new NoSuchAdminException("잘못된 아이디입니다.");
        Admin admin = optionalAdmin.get();
        if(!passwordEncoder.matches(loginReqDto.getPassword(), admin.getPassword())) {
            throw new NoSuchAdminException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(admin.getAdminId(), admin.getRole());
    }
}
