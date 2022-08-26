package com.capg.bcm.loanorigination.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import com.capg.bcm.loanorigination.dto.MambuClientDetailsUpdate;
import com.capg.bcm.loanorigination.exception.InternalServerException;
import com.capg.bcm.loanorigination.exception.MambuError;
import com.capg.bcm.loanorigination.service.MambuClientDetailsUpdateService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class MambuClientDetailsUpdateServiceImpl implements MambuClientDetailsUpdateService {

	private WebClient webClient;

	@Autowired
	public MambuClientDetailsUpdateServiceImpl(@Value("${mambu.api-access.userid}") String mambuApiUserId,
			@Value("${mambu.api-access.password}") String mambuApiPassword,
			@Value("${mambu.api-access.url}") String url, WebClient.Builder builder) {
		webClient = builder.baseUrl(url)
				.filter(ExchangeFilterFunctions.basicAuthentication(mambuApiUserId, mambuApiPassword)).build();
	}

	@Override
	public Mono<Void> updateCustomerDetails(MambuClientDetailsUpdate[] mambuClientDetailsUpdates, String clientId) {

		log.info("Updating the Customer Details in Mambu for customer Id: " + clientId);

		return webClient.patch().uri("/api/clients/{clientId}", clientId)
				.header(HttpHeaders.CONTENT_TYPE, "application/json")
				.header(HttpHeaders.ACCEPT, "application/vnd.mambu.v2+json")
				.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.body(Mono.just(mambuClientDetailsUpdates), MambuClientDetailsUpdate.class).retrieve()
				.onStatus(HttpStatus::is4xxClientError, this::handleCustomerCreationErrors)
				.onStatus(HttpStatus::is5xxServerError, this::handleCustomerCreationErrors).bodyToMono(Void.class);
	}

	private Mono<? extends Throwable> handleCustomerCreationErrors(ClientResponse clientResponse) {
		Mono<MambuError> errorMessage = clientResponse.bodyToMono(MambuError.class);
		return errorMessage.flatMap(message -> {
			throw new InternalServerException(
					message.getErrors().get(0).getErrorReason() + ": " + message.getErrors().get(0).getErrorSource());

		});
	}
}
