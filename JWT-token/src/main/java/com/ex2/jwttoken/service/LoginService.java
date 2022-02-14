package com.ex2.jwttoken.service;

import com.ex2.jwttoken.config.AdminDetail;
import com.ex2.jwttoken.domain.Admin;
import com.ex2.jwttoken.dto.LoginReqDto;
import com.ex2.jwttoken.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final AdminRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Optional<Admin> findUser(LoginReqDto loginReqDto) {
        return repository.findByAdminId(loginReqDto.getAdminId());
    }

    @Transactional
    public Long joinUser(LoginReqDto user) {
        Admin newUser = Admin.builder()
            .adminId(user.getAdminId())
            .password(passwordEncoder.encode(user.getPassword()))
            .build();
        repository.save(newUser);
        return newUser.getAdminNum();
    }

    @Override
    public AdminDetail loadUserByUsername(String adminId) {
        return repository.findByAdminId(adminId).map(Admin::toAdminDetail)
                .orElseThrow(() -> new UsernameNotFoundException("유저 id를 찾을 수 없습니다"));
    }
}
