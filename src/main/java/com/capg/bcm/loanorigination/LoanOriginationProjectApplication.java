package com.capg.bcm.loanorigination;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

/**
 * @author durgeshs
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class LoanOriginationProjectApplication {

	/**
	 * This is the main method for running the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(LoanOriginationProjectApplication.class, args);
	}

	/**
	 * This method is used for generating the swagger
	 * 
	 * @return {@link OpenAPI}
	 */
	@Bean
	public OpenAPI springShopOpenAPI() {
		Server server = new Server();
		server.setUrl("http://localhost:8085/loan-origination-project");
		Server server2 = new Server();
		server2.setUrl("https://loan.capgdigibank.org/");
		return new OpenAPI().servers(List.of(server2, server))
				.info(new Info().title("Etronika Loan Origination APIs")
						.description("Etronika Loan Origination APIs for Capgdigibank").version("v0.0.1")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}

}
