package redis.lockexample.redislock.portfolio;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.lockexample.redislock.domain.portfolio.Portfolio;
import redis.lockexample.redislock.domain.portfolio.PortfolioLikeService;
import redis.lockexample.redislock.domain.portfolio.PortfolioRepository;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PortfolioLikeLockTest {

	@Autowired
	private PortfolioLikeService portfolioLikeService;

	@Autowired
	private PortfolioRepository portfolioRepository;

	private Portfolio portfolio;

	@BeforeEach
	void setUp() {
		portfolio = new Portfolio("TBZ: Generation");
		portfolioRepository.save(portfolio);
	}

	@AfterEach
	void tearDown() {
		portfolioRepository.deleteAll();
	}

	@Test
	@DisplayName("[성공/분산락 적용] 좋아요 증가 동시성 테스트")
	void portfolio_increase_with_lock() throws InterruptedException {
		int numberOfThreads = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);

		for (int i = 0; i < numberOfThreads; i++) {
			executorService.submit(() -> {
				try {
					// 분산락 적용 메서드 호출 (락의 key는 쿠폰의 name으로 설정)
					portfolioLikeService.increaseLikeCount(portfolio.getTitle(), portfolio.getId());
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Portfolio persistPortfolio = portfolioRepository.findById(portfolio.getId())
				.orElseThrow(IllegalArgumentException::new);

		assertThat(persistPortfolio.getLikeCount()).isEqualTo(numberOfThreads);
		System.out.println("좋아요 수 = " + persistPortfolio.getLikeCount());
	}

	@Test
	@DisplayName("[실패/분산락 적용] 좋아요 증가 동시성 테스트")
	void portfolio_increase_without_lock() throws InterruptedException {
		int numberOfThreads = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);

		for (int i = 0; i < numberOfThreads; i++) {
			executorService.submit(() -> {
				try {
					// 분산락 적용 메서드 호출 (락의 key는 쿠폰의 name으로 설정)
					portfolioLikeService.increaseLikeCountWithoutLock(portfolio.getId());
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Portfolio persistPortfolio = portfolioRepository.findById(portfolio.getId())
				.orElseThrow(IllegalArgumentException::new);

		assertThat(persistPortfolio.getLikeCount()).isEqualTo(numberOfThreads);
		System.out.println("좋아요 수 = " + persistPortfolio.getLikeCount());
	}
}
