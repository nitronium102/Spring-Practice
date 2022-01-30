package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
// @Table(name="USER")
public class Member {

	@Id
	// @Column(name="username")
	private Long id;
	private String name;

	// 기본 생성자 요함
	public Member() {}

	public Member(Long id, String name){
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
