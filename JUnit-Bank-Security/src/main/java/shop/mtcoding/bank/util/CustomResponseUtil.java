package shop.mtcoding.bank.util;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.bank.dto.ResponseDto;

public class CustomResponseUtil {

	private static final Logger log = LoggerFactory.getLogger(CustomResponseUtil.class);

	public static void unAuthentication(HttpServletResponse response, String msg) {
		try {// 응답을 JSON으로 바꿔주기
			ObjectMapper om = new ObjectMapper();
			ResponseDto<?> responseDto = new ResponseDto<>(-1, msg, null);
			String responseBody = om.writeValueAsString(responseDto);

			response.setContentType("application/json; charset=utf-8");
			response.setStatus(401);
			response.getWriter().println(responseBody);
		} catch (Exception e) {
			log.error("서버 파싱 에러");
		}
	}

}
