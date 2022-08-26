package com.capg.bcm.loanorigination.dto.mambu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Asset {
	private int amount;
	private String assetName;
	private String depositAccountKey;
	private String encodedKey;
	private String guarantorKey;
	private String guarantorType;
}