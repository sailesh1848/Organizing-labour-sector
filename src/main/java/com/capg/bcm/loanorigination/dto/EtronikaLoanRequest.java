package com.capg.bcm.loanorigination.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author csailesh
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EtronikaLoanRequest {

	private String applicationNo;

	private double amount;

	private int term;

	private double administrationFee;

	private String identifier;

	private String name;

	private String lastName;

	private String phoneNumber;

	private String email;

	private double incomePerMonth;

	private MaritalStatus maritalStatus;

	private int familySize;

	private double liabilitiesPerMonth;

	private double liabilitiesPerMonthConsumerLoan;

	private int childCount;

	private String regionId;

	private int minLivingCosts;

	private LocalDateTime employedFrom;

	private String employerName;

	private String disbursementAccountNumber;

	private double interestRate;

}
