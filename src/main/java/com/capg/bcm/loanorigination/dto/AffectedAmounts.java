/**
 * 
 */
package com.capg.bcm.loanorigination.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author csailesh
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AffectedAmounts {
	private double principalAmount;
	private int interestAmount;
	private int interestFromArrearsAmount;
	private int deferredInterestAmount;
	private int feesAmount;
	private int penaltyAmount;
	private int fundersInterestAmount;
	private int organizationCommissionAmount;

}
