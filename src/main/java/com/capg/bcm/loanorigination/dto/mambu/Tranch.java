package com.capg.bcm.loanorigination.dto.mambu;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tranch {
	private int amount;
	private DisbursementDetails disbursementDetails;
	private String encodedKey;
	private List<Fee> fees;
	private int trancheNumber;
}
