package com.capg.bcm.loanorigination.dto;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public class Loan {
	private String type;
	private String id;

	@JsonProperty("product_id")
	private String productId;

	private String account;
	private String status;
	private String currency;
	private double amount;

	@JsonProperty("amount_balance")
	private double amountBalance;

	@JsonInclude(value = Include.NON_DEFAULT)
	@JsonProperty("amount_unsued")
	private double amountUnsued;

	@JsonProperty("interest_rate")
	private double interestRate;

	@JsonProperty("interest_calculated")
	private double interestCalculated;

	@JsonProperty("start_date")
	private OffsetDateTime startDate;

	@JsonProperty("end_date")
	private OffsetDateTime endDate;

	@JsonProperty("account_principal")
	private String accountPrincipal;

	@JsonProperty("account_interest")
	private String accountInterest;

	@JsonProperty("date_next_payment")
	private OffsetDateTime dateNextPayment;

	@JsonInclude(value = Include.NON_DEFAULT)
	private List<Payment> payments;
	@JsonInclude(value = Include.NON_DEFAULT)
	private List<Schedule> schedule;

}
