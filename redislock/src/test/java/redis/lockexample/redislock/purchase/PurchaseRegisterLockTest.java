package redis.lockexample.redislock.purchase;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.lockexample.redislock.domain.purchase.PurchaseRepository;
import redis.lockexample.redislock.domain.purchase.PurchaseService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PurchaseRegisterLockTest {

	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private PurchaseRepository purchaseRepository;

	private String PURCHASE_CODE;

	@BeforeEach
	void setUp() {
		PURCHASE_CODE = "TBZ_001";
	}

	@AfterEach
	void tearDown() {
		purchaseRepository.deleteAll();
	}

	/*
	 * Feature : 발주 등록 동시성 테스트
	 * Scenario : TBZ_001이라는 이름의 발주 10개가 동시에 등록 요청됨
	 			Lock의 이름은 TBZ_001이라는 발주코드로 설정
	 * Then : 중복된 발주 10개가 동시에 들어오더라도 한 건만 등록되어야 함
	 */
	@Test
	@DisplayName("[성공/분산락 적용] 발주등록 동시성 테스트")
	void register_with_lock() throws InterruptedException {
		int numberOfThreads = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);

		for (int i = 0; i < numberOfThreads; i++) {
			executorService.submit(() -> {
				try {
					// 분산락 적용 메서드 호출 (락의 key는 쿠폰의 name으로 설정)
					purchaseService.registerWithLock(PURCHASE_CODE, PURCHASE_CODE);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Long totalCount = purchaseRepository.countByCode(PURCHASE_CODE);

		System.out.println("등록된 발주 = " + totalCount);
		assertThat(totalCount).isOne();
	}

	@Test
	@DisplayName("[실패/분산락 미적용] 발주등록 동시성 테스트")
	void register_without_lock() throws InterruptedException {
		int numberOfThreads = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);

		for (int i = 0; i < numberOfThreads; i++) {
			executorService.submit(() -> {
				try {
					// 분산락 미적용 메서드 호출
					purchaseService.registerWithoutLock(PURCHASE_CODE);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Long totalCount = purchaseRepository.countByCode(PURCHASE_CODE);

		System.out.println("등록된 발주 = " + totalCount);
		assertThat(totalCount).isOne();
	}
}
