/**
 * 
 */
package com.capg.bcm.loanorigination.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author csailesh
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MambuDisbursementResponse {

	private String encodedKey;
	private String id;
	private String creationDate;
	private String valueDate;
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
	private TransferDetails transferDetails;
	private List<Object> fees;
	private Currency currency;

}
