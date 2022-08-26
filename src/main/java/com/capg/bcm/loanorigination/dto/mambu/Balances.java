package com.capg.bcm.loanorigination.dto.mambu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Balances {
	private int feesBalance;
	private int feesDue;
	private int feesPaid;
	private int holdBalance;
	private int interestBalance;
	private int interestDue;
	private int interestFromArrearsBalance;
	private int interestFromArrearsDue;
	private int interestFromArrearsPaid;
	private int interestPaid;
	private int penaltyBalance;
	private int penaltyDue;
	private int penaltyPaid;
	private int principalBalance;
	private int principalDue;
	private int principalPaid;
	private int redrawBalance;
}
