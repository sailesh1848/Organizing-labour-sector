package com.capg.bcm.loanorigination.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import com.capg.bcm.loanorigination.dto.Loan;
import com.capg.bcm.loanorigination.dto.mambu.Balances;
import com.capg.bcm.loanorigination.dto.mambu.MambuLoanResponse;
import com.capg.bcm.loanorigination.exception.InternalServerException;
import com.capg.bcm.loanorigination.exception.MambuError;
import com.capg.bcm.loanorigination.exception.ResourceNotFoundException;
import com.capg.bcm.loanorigination.service.MambuLoansListService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class MambuLoansListServiceImpl implements MambuLoansListService {

	private WebClient webClient;

	@Autowired
	private MambuLoansListServiceImpl(@Value("${mambu.api-access.userid}") String mambuApiUserId,
			@Value("${mambu.api-access.password}") String mambuApiPassword,
			@Value("${mambu.api-access.url}") String url, WebClient.Builder builder) {
		this.webClient = builder.baseUrl(url)
				.filter(ExchangeFilterFunctions.basicAuthentication(mambuApiUserId, mambuApiPassword)).build();
	}

	@Override
	public Mono<MambuLoanResponse[]> loansList(String customerId) {
		log.info("Recieved request for loan account creation in mambu for customer: " + customerId);
		return this.webClient.get()
				.uri(uriBuilder -> uriBuilder.path("/api/loans").queryParam("detailsLevel", "FULL")
						.queryParam("accountHolderId", customerId).queryParam("accountHolderType", "CLIENT").build())
				.header(HttpHeaders.ACCEPT, "application/vnd.mambu.v2+json").retrieve()
				.onStatus(HttpStatus::is4xxClientError, this::handle4xxErrors)
				.onStatus(HttpStatus::is5xxServerError, this::handle5xxErrors).bodyToMono(MambuLoanResponse[].class);
	}

	@Override
	public Loan mambuEtronikaMapper(MambuLoanResponse mambuLoanResponse) {
		Loan loan = new Loan();

		loan.setCurrency(mambuLoanResponse.getCurrency().getCode());
		loan.setType("STANDARD");
		loan.setId(mambuLoanResponse.getId());
		loan.setAmount(mambuLoanResponse.getLoanAmount());
		loan.setInterestRate(mambuLoanResponse.getInterestSettings().getInterestRate());
		Balances balances = mambuLoanResponse.getBalances();
		loan.setInterestCalculated(balances.getInterestBalance() + balances.getInterestPaid());
		loan.setStartDate(mambuLoanResponse.getDisbursementDetails().getDisbursementDate());
		loan.setProductId("RLPROD");

		if (mambuLoanResponse.getAccountState().equals("ACTIVE_IN_ARREARS")) {
			loan.setStatus("PASTDUE");
		} else {
			loan.setStatus(mambuLoanResponse.getAccountState());
		}
		loan.setAmountBalance(mambuLoanResponse.getBalances().getPrincipalBalance()
				+ mambuLoanResponse.getBalances().getPenaltyBalance() + mambuLoanResponse.getBalances().getFeesBalance()
				+ mambuLoanResponse.getBalances().getInterestBalance()
				+ (double) mambuLoanResponse.getBalances().getHoldBalance());
		return loan;
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
