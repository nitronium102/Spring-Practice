package com.example.restfulwebservice.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

	@Id
	@GeneratedValue
	private Integer id;

	private String description;

	// User : Post -> 1 : (0~N)
	// Main : Sub -> Parent : Child
	// 지연 로딩 : 사용자 엔티티 조회를 할 때 POST 엔티티가 로딩되는 것 x
	// post entity가 조회될 때 user entity를 조회하는 것!
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private User user;

}
