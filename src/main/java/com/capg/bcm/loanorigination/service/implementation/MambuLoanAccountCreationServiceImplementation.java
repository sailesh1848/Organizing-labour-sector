package com.capg.bcm.loanorigination.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import com.capg.bcm.loanorigination.dto.EtronikaLoanRequest;
import com.capg.bcm.loanorigination.dto.MambuApproveRequest;
import com.capg.bcm.loanorigination.dto.MambuClientResponse;
import com.capg.bcm.loanorigination.dto.MambuLoanAccountRequest;
import com.capg.bcm.loanorigination.dto.mambu.MambuLoanResponse;
import com.capg.bcm.loanorigination.exception.InternalServerException;
import com.capg.bcm.loanorigination.exception.MambuError;
import com.capg.bcm.loanorigination.exception.ResourceNotFoundException;
import com.capg.bcm.loanorigination.mapping.MambuLoanRequestMapper;
import com.capg.bcm.loanorigination.service.LoanAccountCreationService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author durgeshs
 *
 */
@Service
@Slf4j
public class MambuLoanAccountCreationServiceImplementation implements LoanAccountCreationService {

	WebClient webClient;

	MambuLoanRequestMapper mambuLoanRequestMapper;

	@Autowired
	MambuLoanAccountCreationServiceImplementation(@Value("${mambu.api-access.userid}") String mambuApiUserId,
			@Value("${mambu.api-access.password}") String mambuApiPassword,
			@Value("${mambu.api-access.url}") String url, MambuLoanRequestMapper mambuLoanRequestMapper,
			WebClient.Builder builder) {
		this.webClient = builder.baseUrl(url)
				.filter(ExchangeFilterFunctions.basicAuthentication(mambuApiUserId, mambuApiPassword)).build();
		this.mambuLoanRequestMapper = mambuLoanRequestMapper;
	}

	/**
	 * This method is used for creation of loan in Mambu
	 * 
	 * @param {@link MambuLoanAccountRequest}
	 * @return {@link MambuLoanResponse}
	 *
	 */
	@Override
	public Mono<MambuLoanResponse> createLoan(MambuLoanAccountRequest mambuLoanAccountRequest) {
		log.info("Recieved request for loan account creation in mambu for customer: "
				+ mambuLoanAccountRequest.getAccountHolderKey());
		return this.webClient.post().uri("/api/loans").header(HttpHeaders.ACCEPT, "application/vnd.mambu.v2+json")
				.body(Mono.just(mambuLoanAccountRequest), MambuLoanAccountRequest.class).retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> handle4xxErrors(clientResponse))
				.onStatus(HttpStatus::is5xxServerError, clientResponse -> handle5xxErrors(clientResponse))
				.bodyToMono(MambuLoanResponse.class);
	}

	/**
	 * 
	 * This method is used to get the client Encoded key
	 * 
	 * @param {@link EtronikaLoanRequest}
	 * @return {@link MambuClientResponse}
	 *
	 */
	@Override
	public Mono<MambuClientResponse> getClient(EtronikaLoanRequest etronikaLoanRequest) {
		log.info("Returning the client details for customer: " + etronikaLoanRequest.getIdentifier());
		Mono<MambuClientResponse> mambuClientResponse = this.webClient.get()
				.uri("/api/clients/" + etronikaLoanRequest.getIdentifier())
				.header(HttpHeaders.ACCEPT, "application/vnd.mambu.v2+json").retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> handle4xxErrors(clientResponse))
				.onStatus(HttpStatus::is5xxServerError, clientResponse -> handle5xxErrors(clientResponse))
				.bodyToMono(MambuClientResponse.class);

		return mambuClientResponse;
	}

	/**
	 * This method is used for approving the loan account
	 * 
	 * @param loanAccountId, {@link MambuApproveRequest}
	 * @return {@link MambuLoanResponse}
	 *
	 */
	@Override
	public Mono<MambuLoanResponse> approveRequest(String loanAccountId, MambuApproveRequest mambuApproveRequest) {
		log.info("Approving the Loan Account: " + loanAccountId);

		Mono<MambuLoanResponse> voidMono = this.webClient.post().uri("/api/loans/" + loanAccountId + ":changeState")
				.header(HttpHeaders.ACCEPT, "application/vnd.mambu.v2+json")
				.body(Mono.just(mambuApproveRequest), MambuApproveRequest.class).retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> handle4xxErrors(clientResponse))
				.onStatus(HttpStatus::is5xxServerError, clientResponse -> handle5xxErrors(clientResponse))
				.bodyToMono(MambuLoanResponse.class);

		return voidMono;
	}

	/**
	 * 
	 * This method is used to generate the {@link MambuApproveRequest} bean.
	 * 
	 * @return {@link MambuApproveRequest}
	 *
	 */
	@Override
	public MambuApproveRequest toMambuApproveRequest() {
		MambuApproveRequest mambuApproveRequest = new MambuApproveRequest("APPROVE", "Approved");
		return mambuApproveRequest;
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
