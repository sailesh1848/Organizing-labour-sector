package com.capg.bcm.loanorigination.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleSettings {

	private int gracePeriod;

	private int repaymentInstallments;

	private List<Integer> fixedDaysOfMonth;

}
