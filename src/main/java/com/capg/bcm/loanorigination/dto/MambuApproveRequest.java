package com.capg.bcm.loanorigination.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MambuApproveRequest {

	private String action;

	private String notes;

}
