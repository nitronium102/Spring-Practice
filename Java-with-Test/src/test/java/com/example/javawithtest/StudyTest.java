package com.example.javawithtest;

import jdk.jfr.Enabled;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Convert;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

	@FastTest // 오타를 줄이는 용도로 사용가능
	@DisplayName("스터디 만들기 fast")
	void create_new_study() { // java reflection을 사용하면 private, default인 메소드에서 접근 가능

		String test_env = System.getenv("TEST_ENV");
	}

	@SlowTest
	@DisplayName("스터디 만들기 slow")
	void create1() { // java reflection을 사용하면 private, default인 메소드에서 접근 가능
		System.out.println("create2");
	}

	@DisplayName("스터디 만들기")
	@RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
	void repeatTest(RepetitionInfo repetitionInfo) {
		System.out.println("test" + repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
	}

	@DisplayName("스터디 만들기")
	@ParameterizedTest(name = "{index} {displayName} message={0}") // parameter를 인덱스로 설정 가능
	@ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요."}) // 1개의 parameter
	void parameterizedTest(String message) {
		System.out.println(message);
	}

	/* argumentAggregator */
	@DisplayName("스터디 만들기3")
	@ParameterizedTest(name = "{index} {displayName} message={0}")
	@CsvSource({"10, '자바 스터디'", "20, 스프링"})
	@NullAndEmptySource
	void aggregationTest(@AggregateWith(StudyAggregator.class) Study study) {
		System.out.println(study);
	}

	static class StudyAggregator implements ArgumentsAggregator { // static inner / public class여야 함

		@Override
		public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
			return new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
		}
	}

	/* argumentConvereter */
	@DisplayName("스터디 만들기2")
	@ParameterizedTest(name = "{index} {displayName} message={0}")
	@ValueSource(ints = {10, 20, 40})
	@NullAndEmptySource
	void convertionTest(@ConvertWith(StudyConverter.class) Study study) {
		System.out.println(study.getLimit());
	}

	static class StudyConverter extends SimpleArgumentConverter {

		@Override
		protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
			assertEquals(Study.class, targetType, "Can only convert to Study");
			return new Study(Integer.parseInt(source.toString()), "이름");
		}
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