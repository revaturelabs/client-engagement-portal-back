package com.engagement;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableSwagger2
@SpringBootApplication
@EnableFeignClients(basePackages = "com.engagement.repo.caliber")
public class ClientEngagementPortalBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientEngagementPortalBackApplication.class, args);
	}
	/**
	 * Configures Swagger UI to not allow trying out APIs that would change data
	 * @return new UI configuration 
	 */
	@Bean
	public UiConfiguration uiConfig() {
		final String[] methodsWithTryItOutButton = {}; //add get method if security allows
		return UiConfigurationBuilder.builder().supportedSubmitMethods(methodsWithTryItOutButton).build();
	}
	
	/**
	 * Configures Swagger UI to make it all pretty-like.
	 *
	 * @return A prepared Docket instance
	 */
	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()				
				.apis(RequestHandlerSelectors.basePackage("com.engagement.controller"))
				.build()
				.apiInfo(apiDetails());
	}
	/**
	 * Dictates API Info for swaggerConfiguration() 
	 * @return new API Info
	 */
	private ApiInfo apiDetails() {
		return new ApiInfo("Client Engagment Portal API", "API for Revature's Client Engagement Portal", "0.1", "All Rights Reserved", new springfox.documentation.service.Contact("Batch2009", "https://github.com/revaturelabs/client-engagement-portal-back", "matthew.thomas@revature.net"), "Revature", "https://revature.com", Collections.emptyList());
		
	}
}
