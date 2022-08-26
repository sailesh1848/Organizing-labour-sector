package com.capg.bcm.loanorigination.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MambuClientDetailsUpdate {
	private String op;

	private String path;

	private Object value;
}
