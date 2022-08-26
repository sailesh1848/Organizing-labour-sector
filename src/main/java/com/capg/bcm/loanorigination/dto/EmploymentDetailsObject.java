package com.capg.bcm.loanorigination.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentDetailsObject {

	@JsonProperty("Employer_Name_Clients")
	public String employerNameClients;

	@JsonProperty("Employment_Start_Date_Clients")
	public String employmentStartDateClients;

	@JsonProperty("Income_Per_Month_Clients")
	public String incomePerMonthClients;
}
