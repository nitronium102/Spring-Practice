package com.example.javawithtest;

import jdk.jfr.Enabled;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

	@Test
	@EnabledOnOs({OS.WINDOWS, OS.MAC})
	@EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "local")
	void create_new_study() { // java reflection을 사용하면 private, default인 메소드에서 접근 가능

		String test_env = System.getenv("TEST_ENV");
		assumeTrue("LOCAL".equalsIgnoreCase(test_env));
		assumingThat("Local".equalsIgnoreCase(test_env), () -> {

		});

		assumingThat("nitronium".equalsIgnoreCase(test_env), () -> {

		});
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