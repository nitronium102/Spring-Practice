package redis.lockexample.redislock.global;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect // AOP로 동작하도록 표시
@Component
@RequiredArgsConstructor
@Slf4j
public class DistributedLockApp {
	private static final String REDISSION_LOCK_PREFIX = "LOCK:";

	private final RedissonClient redissonClient;
	private final AopForTransaction aopForTransaction;

	@Around("@annotation(DistributedLock)")
	public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

		// 0) Lock의 이름 가져오기
		String key = createLockKey(signature, joinPoint, distributedLock);
		// 1) 가져온 Lock의 이름으로 RLock 객체 생성
		RLock rLock = redissonClient.getLock(key);

		try {
			// 2) Lock 획득 시도
			// waitTime만큼 기다리면서 락 획득 시도(해당 시간 동안 락을 획득할 수 없다면 false 리턴)
			// 락을 획득하고 leaseTime이 지나면 락이 자동으로 해제 (leaseTime을 -1로 설정하면 락을 수동으로 해제해야 함)
			boolean isAvailable = acquireLock(rLock, distributedLock);
			if (!isAvailable) {
				log.info("현재 Redisson Lock을 획득할 수 없습니다.");
				return false;
			}
			log.info("Redisson Lock 획득에 성공했습니다.");
			// 3) DistributedLock 어노테이션이 선언된 메서드를 별도의 트랜잭션으로 실행
			return aopForTransaction.proceed(joinPoint); // 대상 객체의 메서드 실행 -> 해당 코드 전후로 공통 기능을 위한 코드 위치시킴
		} finally {
			releaseLock(rLock);
		}
	}

	private String createLockKey(MethodSignature signature, ProceedingJoinPoint joinPoint, DistributedLock distributedLock) {
		return REDISSION_LOCK_PREFIX + CustomSpringELParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), distributedLock.key());
	}

	private boolean acquireLock(RLock rLock, DistributedLock distributedLock) throws InterruptedException {
		try {
			return rLock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit());
		} catch (InterruptedException e) {
			throw new InterruptedException();
		}
	}

	private void releaseLock(RLock rLock) {
		try {
			// 4) 종료 시 무조건 락을 해제
			rLock.unlock();
		} catch (IllegalMonitorStateException e){
			log.info("이미 Redisson Lock이 해제되었습니다.");
		}
	}
}
