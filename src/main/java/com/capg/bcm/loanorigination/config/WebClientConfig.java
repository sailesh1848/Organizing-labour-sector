package com.capg.bcm.loanorigination.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Durgesh Singh
 *
 */
@Configuration
public class WebClientConfig {

	/**
	 * @return {@link WebClient.Builder}
	 */
	@Primary
	@Bean
	WebClient.Builder webClient() {
		return WebClient.builder();
	}

}
