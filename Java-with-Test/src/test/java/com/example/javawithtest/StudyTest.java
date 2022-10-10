package com.example.javawithtest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

//@ExtendWith(FindSlowTestExtension.class) // 선언적인 등록
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudyTest {

	int value = 1;

	@RegisterExtension
	static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);

	@Order(2)
	@FastTest // 오타를 줄이는 용도로 사용가능
	@DisplayName("스터디 만들기 fast")
	void create_new_study() { // java reflection을 사용하면 private, default인 메소드에서 접근 가능

		String test_env = System.getenv("TEST_ENV");
	}

	@Order(1)
	@SlowTest
	@DisplayName("스터디 만들기 slow")
	void create1() throws InterruptedException { // java reflection을 사용하면 private, default인 메소드에서 접근 가능
		Thread.sleep(1005L);
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