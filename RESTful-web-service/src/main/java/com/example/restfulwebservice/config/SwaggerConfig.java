package com.example.restfulwebservice.config;

import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.*;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@Configuration // 설정 관련 annotation
@EnableSwagger2 // SWAGGER 관련
public class SwaggerConfig {
	// localhost:8088/v2/api-docs
	// localhost:8088/swagger-ui/index.html/

	// Swagger Documentation
	private static final Contact DEFAULT_CONTACT = new Contact("Minji Kang",
		"https://github.com/nitronium102", "nitronium@ewhain.net");

	private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("API TITLE",
		"My User Management REST API Service", "1.0", "urn:tos", DEFAULT_CONTACT,
		"Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());

	// asList : 배열 형태 -> 리스트 형태
	private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
		Arrays.asList("application/json", "application/xml")); // json과 xml 파일 형식을 지원함

	@Bean
	public Docket api(){
		return new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(DEFAULT_API_INFO)
			.produces(DEFAULT_PRODUCES_AND_CONSUMES)
			.consumes(DEFAULT_PRODUCES_AND_CONSUMES);
	}

	@Bean
	public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier, ServletEndpointsSupplier servletEndpointsSupplier, ControllerEndpointsSupplier controllerEndpointsSupplier, EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties, WebEndpointProperties webEndpointProperties, Environment environment) {
		List<ExposableEndpoint<?>> allEndpoints = new ArrayList();
		Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
		allEndpoints.addAll(webEndpoints);
		allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
		allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
		String basePath = webEndpointProperties.getBasePath();
		EndpointMapping endpointMapping = new EndpointMapping(basePath);
		boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment, basePath);
		return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes, corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping, null);
	}


	private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment, String basePath) {
		return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
	}
}
