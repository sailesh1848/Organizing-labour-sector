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
public class AccountBalances {

	private double totalBalance;
	private int advancePosition;
	private int arrearsPosition;
	private int expectedPrincipalRedraw;
	private int redrawBalance;
	private double principalBalance;
}
