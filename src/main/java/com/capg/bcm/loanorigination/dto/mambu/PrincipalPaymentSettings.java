package com.capg.bcm.loanorigination.dto.mambu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrincipalPaymentSettings {
	private int amount;
	private String encodedKey;
	private boolean includeFeesInFloorAmount;
	private boolean includeInterestInFloorAmount;
	private int percentage;
	private int principalCeilingValue;
	private int principalFloorValue;
	private String principalPaymentMethod;
	private int totalDueAmountFloor;
	private String totalDuePayment;
}
