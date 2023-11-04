package redis.lockexample.redislock.domain.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
