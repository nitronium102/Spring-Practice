package redis.lockexample.redislock.global;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class CustomSpringELParser {
	private CustomSpringELParser() {}

	/*
	* String[] parameterNames : 대상 표현식에서 사용될 매개변수의 이름 목록
	* Object[] args : 실제 매개변수 값의 배열 (parameterNames와 동일한 순서로 제공)
	* String key : SpEL로 평가할 표현식 문자열
	*/
	public static Object getDynamicValue(String[] parameterNames, Object[] args, String key) {
		ExpressionParser parser = new SpelExpressionParser(); // SpEL 표현식을 파싱하고 평가하기 위한 parser 생성
		StandardEvaluationContext context = new StandardEvaluationContext(); // SpEL 컨텍스트 설정(표현식에서 사용되는 변수 저장)

		// context에 변수 설정
		for (int i=0; i<parameterNames.length; i++){
			context.setVariable(parameterNames[i], args[i]);
		}

		return parser.parseExpression(key)	// 주어진 key를 SpEL 표현식으로 파싱
				.getValue(context, Object.class); // 표현식을 컨텍스트 내에서 평가하고 결과 값을 반환
	}
}
