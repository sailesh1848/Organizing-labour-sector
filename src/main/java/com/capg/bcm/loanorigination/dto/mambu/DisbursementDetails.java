package com.capg.bcm.loanorigination.dto.mambu;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DisbursementDetails {
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime disbursementDate;
	private String encodedKey;
	private Date expectedDisbursementDate;
	private List<Fee> fees;
	private Date firstRepaymentDate;
	private TransactionDetails transactionDetails;
	private String disbursementTransactionKey;
}
