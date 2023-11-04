package redis.lockexample.redislock.domain.coupon;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
	Optional<Coupon> findById(Long couponId);
}
