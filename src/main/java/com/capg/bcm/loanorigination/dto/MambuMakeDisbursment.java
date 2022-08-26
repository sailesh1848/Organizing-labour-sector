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
public class MambuMakeDisbursment {

	private String bookingDate;
	private String externalId;
	private List<Fee> fees;
	private String firstRepaymentDate;
	private String notes;
	private String originalCurrencyCode;
	private TransactionDetails transactionDetails;
	private TransferDetails transferDetails;
	private String valueDate;

}
