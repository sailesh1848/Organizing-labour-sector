/**
 * 
 */
package com.capg.bcm.loanorigination.dto.mambu;

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
public class ApplyFee {

	private int amount;
	private String bookingDate;
	private String externalId;
	private int installmentNumber;
	private String notes;
	private String predefinedFeeKey;
	private String valueDate;

}
