package com.capg.bcm.loanorigination.dto.mambu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountArrearsSettings {
	private String dateCalculationMethod;
	private String encodedKey;
	private int monthlyToleranceDay;
	private String nonWorkingDaysMethod;
	private String toleranceCalculationMethod;
	private int toleranceFloorAmount;
	private int tolerancePercentageOfOutstandingPrincipal;
	private int tolerancePeriod;
}
