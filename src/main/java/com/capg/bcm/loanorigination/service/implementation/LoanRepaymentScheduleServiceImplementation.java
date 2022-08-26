package com.capg.bcm.loanorigination.service.implementation;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import com.capg.bcm.loanorigination.dto.Amount;
import com.capg.bcm.loanorigination.dto.Interest;
import com.capg.bcm.loanorigination.dto.MambuSchedule;
import com.capg.bcm.loanorigination.dto.MambuScheduleInstallment;
import com.capg.bcm.loanorigination.dto.Payment;
import com.capg.bcm.loanorigination.dto.Schedule;
import com.capg.bcm.loanorigination.dto.Total;
import com.capg.bcm.loanorigination.exception.InternalServerException;
import com.capg.bcm.loanorigination.exception.MambuError;
import com.capg.bcm.loanorigination.exception.ResourceNotFoundException;
import com.capg.bcm.loanorigination.service.LoanRepaymentScheduleService;

import reactor.core.publisher.Mono;

@Service
public class LoanRepaymentScheduleServiceImplementation implements LoanRepaymentScheduleService {

	private WebClient webClient;

	@Autowired
	public LoanRepaymentScheduleServiceImplementation(@Value("${mambu.api-access.userid}") String mambuApiUserId,
			@Value("${mambu.api-access.password}") String mambuApiPassword,
			@Value("${mambu.api-access.url}") String url, WebClient.Builder builder) {
		webClient = builder.baseUrl(url)
				.filter(ExchangeFilterFunctions.basicAuthentication(mambuApiUserId, mambuApiPassword)).build();
	}

	@Override
	public Mono<MambuSchedule> repaymentSchedule(String loanAccountId) {

		Mono<MambuSchedule> mambuSchedule;

		mambuSchedule = this.webClient.get().uri("/api/loans/" + loanAccountId + "/schedule")
				.header(HttpHeaders.ACCEPT, "application/vnd.mambu.v2+json").retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> handle4xxErrors(clientResponse))
				.onStatus(HttpStatus::is5xxServerError, clientResponse -> handle5xxErrors(clientResponse))
				.bodyToMono(MambuSchedule.class);

		return mambuSchedule;

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

	@Override
	public List<Schedule> getEtronikaSchedule(MambuSchedule mambuSchedule, double balance) {
		List<Schedule> etronikaSchedules = new ArrayList<>();

		double effectiveBalance = balance;

		for (int i = 0; i < mambuSchedule.getInstallments().size(); i++) {
			Schedule schedule = new Schedule();
			MambuScheduleInstallment installment = mambuSchedule.getInstallments().get(i);
			schedule.setBalance(effectiveBalance);
			schedule.setPrincipal(installment.getPrincipal().getAmount().getExpected());
			schedule.setInterest(installment.getInterest().getAmount().getExpected());
			schedule.setTotal(installment.getPrincipal().getAmount().getExpected()
					+ installment.getInterest().getAmount().getExpected());
			schedule.setDate(installment.getDueDate());
			effectiveBalance = effectiveBalance - (installment.getPrincipal().getAmount().getExpected()
					+ installment.getInterest().getAmount().getExpected());
			etronikaSchedules.add(schedule);
		}

		return etronikaSchedules;
	}

	@Override
	public List<Payment> getEtronikaNextPaymentSchedule(MambuSchedule mambuSchedule, OffsetDateTime lastPaymentDate) {
		List<Payment> nextPayments = new ArrayList<>();

		Amount amount = new Amount();
		Interest interest = new Interest();
		Total total = new Total();

		for (int i = 0; i < mambuSchedule.getInstallments().size(); i++) {
			OffsetDateTime date2 = mambuSchedule.getInstallments().get(i).getDueDate();
			OffsetDateTime date1 = lastPaymentDate;
			boolean isAfter;
			if (date1 == null) {
				isAfter = true;
			} else {
				isAfter = date2.isAfter(date1);
			}
			if (mambuSchedule.getInstallments().get(i).getRepaidDate() == null) {
				List<MambuScheduleInstallment> mambuScheduleInstallments = mambuSchedule.getInstallments();

				if (isAfter) {
					Payment nextPayment = new Payment();
					amount.setScheduled(mambuScheduleInstallments.get(i).getPrincipal().getAmount().getDue());
					amount.setTotal(mambuScheduleInstallments.get(i).getPrincipal().getAmount().getDue());
					interest.setScheduled(mambuScheduleInstallments.get(i).getInterest().getAmount().getDue());
					interest.setTotal(mambuScheduleInstallments.get(i).getInterest().getAmount().getDue());
					total.setScheduled(mambuScheduleInstallments.get(i).getInterest().getAmount().getDue()
							+ mambuScheduleInstallments.get(i).getPrincipal().getAmount().getDue());
					total.setTotal(mambuScheduleInstallments.get(i).getInterest().getAmount().getDue()
							+ mambuScheduleInstallments.get(i).getPrincipal().getAmount().getDue());
					nextPayment.setAmount(amount);
					nextPayment.setInterest(interest);
					nextPayment.setTotal(total);
					nextPayments.add(nextPayment);
				}
			}

		}

		return nextPayments;
	}

}
