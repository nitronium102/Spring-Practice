package com.ex2.jwttoken.domain;

import com.ex2.jwttoken.config.AdminDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminNum;

    @Column
    private String password;

    @Column
    private String adminId;

    @Column
    private String role;

    @Builder
    public Admin(String adminId, String password) {
        this.adminId = adminId;
        this.password =password;
        role = "ADMIN";
    }

    public AdminDetail toAdminDetail() {
        return AdminDetail.builder()
                .adminId(adminId)
                .auth(role)
                .password(password)
                .build();
    }
}
