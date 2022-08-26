package com.capg.bcm.loanorigination.dto.mambu;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduleSettings {
	private BillingCycle billingCycle;
	private int defaultFirstRepaymentDueDateOffset;
	private List<Integer> fixedDaysOfMonth;
	private int gracePeriod;
	private String gracePeriodType;
	private boolean hasCustomSchedule;
	private List<PaymentPlan> paymentPlan;
	private int periodicPayment;
	private int principalRepaymentInterval;
	private int repaymentInstallments;
	private int repaymentPeriodCount;
	private String repaymentPeriodUnit;
	private String repaymentScheduleMethod;
	private String scheduleDueDatesMethod;
	private String shortMonthHandlingMethod;
}
