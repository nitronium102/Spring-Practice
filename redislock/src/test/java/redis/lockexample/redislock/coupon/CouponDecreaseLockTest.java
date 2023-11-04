package redis.lockexample.redislock.coupon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.lockexample.redislock.domain.coupon.Coupon;
import redis.lockexample.redislock.domain.coupon.CouponRepository;
import redis.lockexample.redislock.domain.coupon.CouponService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CouponDecreaseLockTest {

	@Autowired
	private CouponService couponService;

	@Autowired
	private CouponRepository couponRepository;

	private Coupon coupon;

	@BeforeEach
	void setUp() {
		coupon = new Coupon("더보이즈 콘서트 할인 티켓", 100L);
		couponRepository.save(coupon);
	}

	@Test
	@DisplayName("[성공/분산락 적용] 쿠폰 차감 100명 동시성 테스트")
	void decrease_with_lock() throws InterruptedException {
		int numberOfThreads = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);

		for (int i = 0; i < numberOfThreads; i++) {
			executorService.submit(() -> {
				try {
					// 분산락 적용 메서드 호출 (락의 key는 쿠폰의 name으로 설정)
					couponService.couponDecrease(coupon.getName(), coupon.getId());
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Coupon persistCoupon = couponRepository.findById(coupon.getId())
				.orElseThrow(IllegalArgumentException::new);

		assertThat(persistCoupon.getAvailableStock()).isZero();
		System.out.println("잔여 쿠폰 개수 = " + persistCoupon.getAvailableStock());
	}

	@Test
	@DisplayName("[실패/분산락 미적용] 쿠폰 차감 100명 동시성 테스트")
	void decrease_without_lock() throws InterruptedException {
		int numberOfThreads = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);

		for (int i = 0; i < numberOfThreads; i++) {
			executorService.submit(() -> {
				try {
					// 분산락 미적용 메서드 호출
					couponService.couponDecrease(coupon.getId());
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Coupon persistCoupon = couponRepository.findById(coupon.getId())
				.orElseThrow(IllegalArgumentException::new);

		assertThat(persistCoupon.getAvailableStock()).isZero();
		System.out.println("잔여 쿠폰 개수 = " + persistCoupon.getAvailableStock());
	}
}
