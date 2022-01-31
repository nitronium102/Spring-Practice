package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

	public static void main(String[] args){
		// persistence.xml 파일에 있는 persistenceUnitName 기반
		// application 생성 시에 하나만 생성되어야 함
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManager em = emf.createEntityManager(); // 절대 공유 X
		// 트랜잭션
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			// 저장
			Team team = new Team();
			team.setName("TeamA");
			em.persist(team);

			Member member = new Member();
			member.setUserName("member1");
			member.changeTeam(team);
			em.persist(member);

//			team.addMember(member);
//			em.flush();
//			em.clear();

			// 조회
			Team findTeam = em.find(Team.class, team.getId());
			List<Member> members = findTeam.getMembers();
			for (Member m : members){
				System.out.println("m="+m.getUserName());
			}

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
