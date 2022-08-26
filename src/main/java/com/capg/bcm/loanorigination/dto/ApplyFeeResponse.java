package com.capg.bcm.loanorigination.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplyFeeResponse {

	private String encodedKey;
	private String id;
	private Date creationDate;
	private Date valueDate;
	private String parentAccountKey;
	private String type;
	private double amount;
	private AffectedAmounts affectedAmounts;
	private Taxes taxes;
	private AccountBalances accountBalances;
	private String userKey;
	private String branchKey;
	private Terms terms;
	private TransactionDetails transactionDetails;
	private List<Object> fees;
	private Currency currency;

}
