package com.example.javawithtest;

import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

	@Test
	void create_new_study() { // java reflection을 사용하면 private, default인 메소드에서 접근 가능

		Study actual = new Study(10);
		assertThat(actual.getLimit().isGreaterThan(0));

		// ThreadLocal을 사용하는 경우 다른 thread와 공유가 되지 않기 때문에 예상치 못한 결과가 발생할 수 있음
		assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
			new Study(10);
			Thread.sleep(300);
		});

		IllegalArgumentException exception =
				assertThrows(IllegalArgumentException.class, () -> new Study(-10));
		String message = exception.getMessage();
		assertEquals("limit은 0보다 커야 한다.", exception.getMessage());

		assertAll(
				() -> assertNotNull(study),
				// expected, actual, message 순
				// lambda 식으로 만들면 문자열 연산을 필요할 때만 하게 된다
				() -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
					() -> "스터디를 처음 만들면 " + StudyStatus.DRAFT + "상태다."),
				() -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다")
		);
		System.out.println("create");
	}

	@Test
	@Disabled
	@DisplayName("스터디 만들기2")
	void create1() { // java reflection을 사용하면 private, default인 메소드에서 접근 가능
		System.out.println("create2");
	}

	@BeforeAll // 모든 테스트가 실행이 되기 전에 딱 한 번만 실행
	static void beforeAll() {
		System.out.println("before all");
	}

	@AfterAll
	static void afterAll() {
		System.out.println("after all");
	}

	@BeforeEach
	void beforeEach() {
		System.out.println("before each");
	}

	@AfterEach
	void afterEach() {
		System.out.println("after each");
	}

}