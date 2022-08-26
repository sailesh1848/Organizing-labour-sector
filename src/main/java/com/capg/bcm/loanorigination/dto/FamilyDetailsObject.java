package com.capg.bcm.loanorigination.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyDetailsObject {

	@JsonProperty("No_of_Children_Clients")
	public int noOfChildrenClients;

	@JsonProperty("Marital_Status_Clients")
	public String maritalStatusClients;

	@JsonProperty("Family_Size_Clients")
	public int familySizeClients;
}
