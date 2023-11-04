package redis.lockexample.redislock.domain.portfolio;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import redis.lockexample.redislock.global.DistributedLock;

@RequiredArgsConstructor
@Service
public class PortfolioLikeService {
	private final PortfolioRepository portfolioRepository;

	@DistributedLock(key = "#lockName")
	public void increaseLikeCount(String lockName, Long id) {
		Portfolio portfolio = portfolioRepository.findById(id)
				.orElseThrow(IllegalArgumentException::new);

		portfolio.increaseLikeCount();
	}

	@DistributedLock(key = "#lockName")
	public void decreaseLikeCount(String lockName, Long id) {
		Portfolio portfolio = portfolioRepository.findById(id)
				.orElseThrow(IllegalArgumentException::new);

		portfolio.decreaseLikeCount();
	}

	@Transactional
	public void increaseLikeCountWithoutLock(Long id){
		Portfolio portfolio = portfolioRepository.findById(id)
				.orElseThrow(IllegalArgumentException::new);

		portfolio.increaseLikeCount();
	}
}
