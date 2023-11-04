package redis.lockexample.redislock.domain.portfolio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Portfolio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private Integer likeCount = 0;

	public Portfolio(String title) {
		this.title = title;
	}

	public void increaseLikeCount() {
		this.likeCount += 1;
	}

	public void decreaseLikeCount() {
		validateLikeCount();
		this.likeCount -= 1;
	}

	private void validateLikeCount() {
		if (this.likeCount < 1) {
			throw new IllegalArgumentException();
		}
	}
}
