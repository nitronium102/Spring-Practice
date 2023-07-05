package shop.mtcoding.bank.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc // Mock(가짜) 환경에 MockMvc가 등록됨
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class SecurityConfigTest {

	// 가짜 환경에 등록된 MockMvc를 DI함
	@Autowired
	private MockMvc mvc;

	@Test
	public void authentication_test() throws Exception {
		// given

		// when
		ResultActions resultActions = mvc.perform(get("/api/s/hello"));
		String responseBody = resultActions.andReturn().getResponse().getContentAsString();
		int httpStatusCode = resultActions.andReturn().getResponse().getStatus();
		System.out.println("테스트 : " + responseBody);
		System.out.println("테스트 : " + httpStatusCode);

		// then
	}

	@Test
	public void authorization_test() throws Exception {
		// given

		// when

		// then
	}
}
