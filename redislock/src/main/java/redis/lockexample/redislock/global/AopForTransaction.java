package redis.lockexample.redislock.global;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/*
* AOP에서 트랜잭션 분리를 위한 클래스
*/
@Component
public class AopForTransaction {

	/*
	* 메서드가 실행될 때 새로운 트랜잭션을 시작하도록 지정
	* 이 메서드가 실행될 때, 이미 진행 중인 트랜잭션과 독립적인 새로운 트랜잭션 시작
	*/
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Object proceed(final ProceedingJoinPoint joinPoint) throws Throwable { // joinPoint : advise를 적용 가능한 지점
		return joinPoint.proceed(); // 관점이 적용된 메서드가 실행
	}
}
