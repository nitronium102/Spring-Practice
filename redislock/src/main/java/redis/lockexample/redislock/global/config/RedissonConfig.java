package redis.lockexample.redislock.global.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

	@Value("${spring.data.redis.host}")
	private String redisHost;

	@Value("${spring.data.redis.port}")
	private int redisPort;

	// redission 연결을 위해 호스트 주소 앞에 붙임
	private static final String REDISSION_HOST_PREFIX = "redis://";

	@Bean
	public RedissonClient redissonClient() { // redissonClient를 생성하고 반환
		RedissonClient redissonClient = null;
		Config config = new Config();
		config.useSingleServer().setAddress(REDISSION_HOST_PREFIX + redisHost + ":" + redisPort);
		redissonClient = Redisson.create(config);
		return redissonClient;
	}
}
