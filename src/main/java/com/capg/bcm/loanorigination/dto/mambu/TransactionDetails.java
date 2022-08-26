package com.capg.bcm.loanorigination.dto.mambu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDetails {
	private String encodedKey;
	private boolean internalTransfer;
	private String targetDepositAccountKey;
	private String transactionChannelId;
	private String transactionChannelKey;
}
