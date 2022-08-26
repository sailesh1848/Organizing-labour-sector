package com.capg.bcm.loanorigination.dto;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public class MambuScheduleInstallment {

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dueDate;
	private String encodedKey;
	private MambuScheduleFee fee;
	private List<MambuScheduleFeeDetails> feeDetails;
	private MambuScheduleInterest interest;
	private boolean isPaymentHoliday;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime lastPaidDate;
	private String number;
	private String parentAccountKey;
	private MambuSchedulePenalty penalty;
	private MambuSchedulePricipal principal;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime repaidDate;
	private String state;

}
