package redis.lockexample.redislock.domain.coupon;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import redis.lockexample.redislock.global.DistributedLock;

@Component
@RequiredArgsConstructor
public class CouponService {
	private final CouponRepository couponRepository;

	@Transactional
	public void couponDecrease(Long couponId) {
		Coupon coupon = couponRepository.findById(couponId)
				.orElseThrow(IllegalArgumentException::new);

		coupon.decrease();
	}

	@DistributedLock(key = "#lockName")
	public void couponDecrease(String lockname, Long couponId) {
		Coupon coupon = couponRepository.findById(couponId)
				.orElseThrow(IllegalArgumentException::new);

		coupon.decrease();
	}
}
