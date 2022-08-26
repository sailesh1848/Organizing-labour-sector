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

import com.capg.bcm.loanorigination.dto.ApplyFeeResponse;
import com.capg.bcm.loanorigination.dto.MambuDisbursementResponse;
import com.capg.bcm.loanorigination.dto.MambuMakeDisbursment;
import com.capg.bcm.loanorigination.dto.mambu.ApplyFee;
import com.capg.bcm.loanorigination.exception.InternalServerException;
import com.capg.bcm.loanorigination.exception.MambuError;
import com.capg.bcm.loanorigination.exception.ResourceNotFoundException;
import com.capg.bcm.loanorigination.service.DisbursementService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author csailesh
 *
 */

@Slf4j
@Service
public class DisbursementServiceIMPL implements DisbursementService {

	private WebClient webClient;

	public DisbursementServiceIMPL() {
		super();
	}

	@Autowired
	public DisbursementServiceIMPL(@Value("${mambu.api-access.userid}") String mambuApiUserId,
			@Value("${mambu.api-access.password}") String mambuApiPassword,
			@Value("${mambu.api-access.url}") String url, WebClient.Builder builder) {
		this.webClient = builder.baseUrl(url)
				.filter(ExchangeFilterFunctions.basicAuthentication(mambuApiUserId, mambuApiPassword)).build();
	}

	@Override
	public Mono<MambuDisbursementResponse> initiateDisbursementbyId(MambuMakeDisbursment mambuMakeDisbursment,
			String accountId) {

		log.info("Started Disbursement for account Id: " + accountId);

		return webClient.post().uri("api/loans/" + accountId + "/disbursement-transactions")
				.header(HttpHeaders.ACCEPT, "application/vnd.mambu.v2+json")
				.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(Mono.just(mambuMakeDisbursment), MambuMakeDisbursment.class).retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> handle4xxErrors(clientResponse))
				.onStatus(HttpStatus::is5xxServerError, clientResponse -> handle5xxErrors(clientResponse))
				.bodyToMono(MambuDisbursementResponse.class);

	}

	@Override
	public Mono<ApplyFeeResponse> applyFeeonDisbursementbyId(ApplyFee applyFee, String accountId) {

		log.info("Applying fee for account Id: " + accountId);

		return webClient.post().uri("api/loans/" + accountId + "/fee-transactions")
				.header(HttpHeaders.ACCEPT, "application/vnd.mambu.v2+json")
				.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.body(Mono.just(applyFee), ApplyFee.class).retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> handle4xxErrors(clientResponse))
				.onStatus(HttpStatus::is5xxServerError, clientResponse -> handle5xxErrors(clientResponse))
				.bodyToMono(ApplyFeeResponse.class);

	}

	private Mono<? extends Throwable> handle4xxErrors(ClientResponse clientResponse) {
		Mono<MambuError> errorMessage = clientResponse.bodyToMono(MambuError.class);
		return errorMessage.flatMap(message -> {
			if (clientResponse.rawStatusCode() == 404) {
				throw new ResourceNotFoundException(message.getErrors().get(0).getErrorReason() + ": "
						+ message.getErrors().get(0).getErrorSource());
			} else {
				throw new InternalServerException(message.getErrors().get(0).getErrorReason() + ": "
						+ message.getErrors().get(0).getErrorSource());
			}

		});
	}

	private Mono<? extends Throwable> handle5xxErrors(ClientResponse clientResponse) {
		Mono<MambuError> errorMessage = clientResponse.bodyToMono(MambuError.class);
		return errorMessage.flatMap(message -> {
			throw new InternalServerException(
					message.getErrors().get(0).getErrorReason() + ": " + message.getErrors().get(0).getErrorSource());

		});
	}

}
