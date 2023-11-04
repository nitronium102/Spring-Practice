package redis.lockexample.redislock.domain.purchase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.lockexample.redislock.global.DistributedLock;

@RequiredArgsConstructor
@Service
public class PurchaseService {
	private final PurchaseRepository purchaseRepository;

	@Transactional
	public void registerWithoutLock(String code) {
		boolean existsPurchase = purchaseRepository.existsByCode(code);
		if (existsPurchase) {
			throw new IllegalArgumentException();
		}

		Purchase purchase = new Purchase(code);
		purchaseRepository.save(purchase);
	}

	@DistributedLock(key = "#lockName")
	public void registerWithLock(String lockName, String code) {
		boolean existsPurchase = purchaseRepository.existsByCode(code);
		if (existsPurchase) {
			throw new IllegalArgumentException();
		}

		Purchase purchase = new Purchase(code);
		purchaseRepository.save(purchase);
	}
}
