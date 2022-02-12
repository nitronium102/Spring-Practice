package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor // V1과 V2는 상속관계라 부모의 인스턴스를 상속받게 되는데 현재 User에는 grade를 포함한 기본 생성자가 존재하지 않음
@NoArgsConstructor
@JsonFilter("UserInfoV2") // 부여된 필터값은 controller나 service 클래스에서 사용된다
public class UserV2 extends User{
	private String grade;
}
