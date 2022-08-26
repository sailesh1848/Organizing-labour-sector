package com.capg.bcm.loanorigination.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MambuLoanAccountRequest {

	private String accountHolderKey;

	private String productTypeKey;

	private String accountHolderType;

	private String loanAmount;

	private InterestSettings interestSettings;

	private ScheduleSettings scheduleSettings;

}
