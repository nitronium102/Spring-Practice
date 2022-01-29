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

			// [JPQL]
			// Member 테이블이 아닌 객체를 가져옴!
			List<Member> result = em.createQuery("select m from Member as m", Member.class)
				.setFirstResult(5) // 5번부터
				.setMaxResults(8) // 8개 가져와!
				.getResultList();

			for (Member member : result){
				System.out.println("member.name = "+member.getName());
			}

			// 회원 조회 -> em.persist 없어도 괜찮다!
//			Member findMember = em.find(Member.class, 1L);
//			findMember.setName("HelloJPA");

//			// 회원 삭제
//			Member findMember = em.find(Member.class, 1L);
//			em.remove(findMember);

//			// 회원 조회
//			Member findMember = em.find(Member.class, 1L);
//			System.out.println("findMember.id = " + findMember.getId());
//			System.out.println("findMember.name = " + findMember.getName());

			// 회원 등록
//			Member member = new Member();
//			member.setId(2L);
//			member.setName("helloB");
//			em.persist(member); // 저장

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
