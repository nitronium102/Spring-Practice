package redis.lockexample.redislock.domain.purchase;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	boolean existsByCode(String code);
	long countByCode(String code);
}
