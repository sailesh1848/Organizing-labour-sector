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
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Taxes {

	private int taxOnInterestAmount;
	private int taxOnInterestFromArrearsAmount;
	private int deferredTaxOnInterestAmount;
	private int taxOnFeesAmount;
	private int taxOnPenaltyAmount;

}
