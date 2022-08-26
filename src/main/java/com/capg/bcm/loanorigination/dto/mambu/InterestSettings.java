package com.capg.bcm.loanorigination.dto.mambu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterestSettings {
	private boolean accrueInterestAfterMaturity;
	private boolean accrueLateInterest;
	private String interestApplicationMethod;
	private String interestBalanceCalculationMethod;
	private String interestCalculationMethod;
	private String interestChargeFrequency;
	private int interestRate;
	private int interestRateReviewCount;
	private String interestRateReviewUnit;
	private String interestRateSource;
	private int interestSpread;
	private String interestType;
}
