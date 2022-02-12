package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value={"password", "ssn"}) // 클래스 단위로 필터링
@JsonFilter("UserInfo") // 부여된 필터값은 controller나 service 클래스에서 사용된다
public class User {
	private Integer id;

	@Size(min=2, message = "Name은 2글자 이상 입력해주세요.")
	private String name;

	@Past // 과거 날짜만 가능한 제약 조건
	private Date joinDate;

	//@JsonIgnore // 반환 시 Json값에 포함되지 않음 -> 반환값 제어
	private String password;

	//@JsonIgnore
	private String ssn; // 주민등록번호
}
