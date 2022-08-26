package com.capg.bcm.loanorigination.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiabilitiesDetailsObject {

	@JsonProperty("Liabilities_per_month_Clients")
	public int liabilitiesPerMonthClients;

	@JsonProperty("Liabilities_per_month_for_consum")
	public int liabilitiesPerMonthForConsum;
}
