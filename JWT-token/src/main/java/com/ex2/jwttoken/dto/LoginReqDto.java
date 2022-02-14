package com.ex2.jwttoken.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class LoginReqDto {

    @NotBlank
    private String adminId;

    @NotBlank
    private String password;

    @Builder
    public LoginReqDto(String adminId, String password) {
        this.adminId = adminId;
        this.password = password;
    }

}
