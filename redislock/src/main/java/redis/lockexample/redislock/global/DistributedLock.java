package redis.lockexample.redislock.global;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {

	// 락의 이름
	String key();

	// 락의 시간 단위 : 기본 SECOND
	TimeUnit timeUnit() default TimeUnit.SECONDS;

	/*
	* 락을 획득하기 위해 대기하는 시간 (default 5s)
	*/
	long waitTime() default 5L;

	/*
	* 락 유지 기간 (default 3s)
	* 락 획득 후 leaseTime이 지나면 락을 해제한다
	*/
	long leaseTime() default 3L;
}
