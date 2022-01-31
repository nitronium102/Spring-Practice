package hellojpa;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

	@Id @GeneratedValue
	@Column(name="MEMBER_ID")
	private Long id;

	@Column(name="USERNAME")
	private String userName;

	@ManyToOne(fetch = FetchType.LAZY) // 관계
	@JoinColumn(name="TEAM_ID") // 조인하는 컬럼명
	private Team team;

	@OneToOne
	@JoinColumn(name="LOCKER_ID")
	private Locker locker;

	@OneToMany(mappedBy = "member")
	private List<MemberProduct> memberProducts = new ArrayList<>();

	public void changeTeam(Team team){
		this.team = team;
		team.getMembers().add(this);
	}

}