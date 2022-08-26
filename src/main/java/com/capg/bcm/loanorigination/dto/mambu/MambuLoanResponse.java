package com.capg.bcm.loanorigination.dto.mambu;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MambuLoanResponse {
	private AccountArrearsSettings accountArrearsSettings;
	private String accountHolderKey;
	private String accountHolderType;
	private String accountState;
	private String accountSubState;
	private int accruedInterest;
	private int accruedPenalty;
	private String activationTransactionKey;
	private boolean allowOffset;
	private Date approvedDate;
	private int arrearsTolerancePeriod;
	private List<Asset> assets;
	private String assignedBranchKey;
	private String assignedCentreKey;
	private String assignedUserKey;
	private Balances balances;
	private Date closedDate;
	private Date creationDate;
	private String creditArrangementKey;
	private Currency currency;
	private int daysInArrears;
	private int daysLate;
	private DisbursementDetails disbursementDetails;
	private String encodedKey;
	private List<FundingSource> fundingSources;
	private String futurePaymentsAcceptance;
	private List<Guarantor> guarantors;
	private String id;
	private int interestCommission;
	private int interestFromArrearsAccrued;
	private InterestSettings interestSettings;
	private Date lastAccountAppraisalDate;
	private Date lastInterestAppliedDate;
	private Date lastInterestReviewDate;
	private Date lastLockedDate;
	private Date lastModifiedDate;
	private Date lastSetToArrearsDate;
	private Date lastTaxRateReviewDate;
	private String latePaymentsRecalculationMethod;
	private int loanAmount;
	private String loanName;
	private List<String> lockedOperations;
	private String migrationEventKey;
	private String notes;
	private String originalAccountKey;
	private int paymentHolidaysAccruedInterest;
	private String paymentMethod;
	private PenaltySettings penaltySettings;
	private PrepaymentSettings prepaymentSettings;
	private PrincipalPaymentSettings principalPaymentSettings;
	private String productTypeKey;
	private RedrawSettings redrawSettings;
	private String rescheduledAccountKey;
	private ScheduleSettings scheduleSettings;
	private String settlementAccountKey;
	private int taxRate;
	private Date terminationDate;
	private List<Tranch> tranches;
}
