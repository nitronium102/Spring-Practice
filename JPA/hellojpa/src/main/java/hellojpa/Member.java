package hellojpa;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class Member {

	@Id @GeneratedValue
	@Column(name="MEMBER_ID")
	private Long id;

	@Column(name="USERNAME")
	private String userName;

//	@Column(name="TEAM_ID")
//	private Long teamId;

	@ManyToOne(fetch = FetchType.LAZY) // 관계
	@JoinColumn(name="TEAM_ID") // 조인하는 컬럼명
	private Team team;

	public void changeTeam(Team team){
		this.team = team;
		team.getMembers().add(this);
	}

}