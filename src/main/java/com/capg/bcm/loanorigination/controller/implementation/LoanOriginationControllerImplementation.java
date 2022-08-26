package com.capg.bcm.loanorigination.controller.implementation;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.capg.bcm.loanorigination.controller.LoanOriginationController;
import com.capg.bcm.loanorigination.dto.ApplyFeeResponse;
import com.capg.bcm.loanorigination.dto.EtronikaLoanListRequest;
import com.capg.bcm.loanorigination.dto.EtronikaLoanListResponse;
import com.capg.bcm.loanorigination.dto.EtronikaLoanRequest;
import com.capg.bcm.loanorigination.dto.EtronikaSuccessResponse;
import com.capg.bcm.loanorigination.dto.Loan;
import com.capg.bcm.loanorigination.dto.LoanListResponse;
import com.capg.bcm.loanorigination.service.MambuClientDetailsUpdateService;
import com.capg.bcm.loanorigination.service.MambuLoansListService;
import com.capg.bcm.loanorigination.dto.MambuApproveRequest;
import com.capg.bcm.loanorigination.dto.MambuClientDetailsUpdate;
import com.capg.bcm.loanorigination.dto.MambuLoanAccountRequest;
import com.capg.bcm.loanorigination.dto.MambuMakeDisbursment;
import com.capg.bcm.loanorigination.dto.MambuSchedule;
import com.capg.bcm.loanorigination.dto.Payment;
import com.capg.bcm.loanorigination.dto.Schedule;
import com.capg.bcm.loanorigination.dto.mambu.ApplyFee;
import com.capg.bcm.loanorigination.dto.mambu.Balances;
import com.capg.bcm.loanorigination.dto.mambu.MambuLoanResponse;
import com.capg.bcm.loanorigination.exception.BadRequestException;
import com.capg.bcm.loanorigination.mapping.ApplyFeeMapper;
import com.capg.bcm.loanorigination.mapping.EtronikaMambuClientUpdateMapper;
import com.capg.bcm.loanorigination.mapping.MakeDisburesmentMapper;
import com.capg.bcm.loanorigination.mapping.MambuLoanRequestMapper;
import com.capg.bcm.loanorigination.service.DisbursementService;
import com.capg.bcm.loanorigination.service.LoanAccountCreationService;
import com.capg.bcm.loanorigination.service.LoanRepaymentScheduleService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author durgeshs
 *
 */
@RestController
@Slf4j
public class LoanOriginationControllerImplementation implements LoanOriginationController {

	private MambuClientDetailsUpdateService mambuClientDetailsUpdateService;

	private EtronikaMambuClientUpdateMapper etronikaMambuClientUpdateMapper;

	private LoanAccountCreationService loanAccountCreationService;

	private MambuLoanRequestMapper mambuLoanRequestMapper;

	private MakeDisburesmentMapper makeDisburesmentMapper;

	private ApplyFeeMapper applyFeeMapper;

	private DisbursementService disbursementService;

	private MambuLoansListService mambuLoansListService;

	private LoanRepaymentScheduleService loanRepaymentScheduleService;

	@Autowired
	public LoanOriginationControllerImplementation(MambuClientDetailsUpdateService mambuClientDetailsUpdateService,
			EtronikaMambuClientUpdateMapper etronikaMambuClientUpdateMapper,
			LoanAccountCreationService loanAccountCreationService, MambuLoanRequestMapper mambuLoanRequestMapper,
			MakeDisburesmentMapper makeDisburesmentMapper, ApplyFeeMapper applyFeeMapper,
			DisbursementService disbursementService, LoanRepaymentScheduleService loanRepaymentScheduleService,
			MambuLoansListService mambuLoansListService) {
		this.mambuClientDetailsUpdateService = mambuClientDetailsUpdateService;
		this.etronikaMambuClientUpdateMapper = etronikaMambuClientUpdateMapper;
		this.loanAccountCreationService = loanAccountCreationService;
		this.mambuLoanRequestMapper = mambuLoanRequestMapper;
		this.makeDisburesmentMapper = makeDisburesmentMapper;
		this.applyFeeMapper = applyFeeMapper;
		this.disbursementService = disbursementService;
		this.loanRepaymentScheduleService = loanRepaymentScheduleService;
		this.mambuLoansListService = mambuLoansListService;
	}

	@Override
	public Mono<EtronikaSuccessResponse> createLoan(EtronikaLoanRequest etronikaLoanRequest) {

		log.info("Received request from etronika for loan account creation of customer: "
				+ etronikaLoanRequest.getIdentifier());

		return loanAccountCreationService.getClient(etronikaLoanRequest).flatMap(mambuClientResponse -> {
			MambuLoanAccountRequest mambuLoanAccountRequest = mambuLoanRequestMapper
					.toMambuLoanAccountRequest(etronikaLoanRequest, mambuClientResponse);
			return loanAccountCreationService.createLoan(mambuLoanAccountRequest);
		}).flatMap(mambuLoanResponse -> {
			if (Objects.isNull(mambuLoanResponse)) {
				throw new BadRequestException("Bad request");
			}
			String loanAccountId = mambuLoanResponse.getId();
			MambuApproveRequest mambuApproveRequest = loanAccountCreationService.toMambuApproveRequest();
			return loanAccountCreationService.approveRequest(loanAccountId, mambuApproveRequest);
		}).flatMap(response -> {
			MambuMakeDisbursment mambuMakeDisbursment = makeDisburesmentMapper
					.toMambuMakeDisbursment(etronikaLoanRequest);
			String loanAccountId = response.getId();
			return disbursementService.initiateDisbursementbyId(mambuMakeDisbursment, loanAccountId)
					.flatMap(mambuDisbursementResponse -> {
						ApplyFee applyFee = applyFeeMapper.toApplyFee(etronikaLoanRequest);
						if (applyFee.getAmount() <= 0) {
							return Mono.just(new ApplyFeeResponse());
						} else {
							return disbursementService.applyFeeonDisbursementbyId(applyFee, loanAccountId);
						}
					});
		}).flatMap(applyFeeResponse -> {
			MambuClientDetailsUpdate mambuClientDetailsUpdate1 = etronikaMambuClientUpdateMapper
					.toMambuClientEmploymentDetails(
							etronikaMambuClientUpdateMapper.toEmploymentDetailsObject(etronikaLoanRequest));
			MambuClientDetailsUpdate mambuClientDetailsUpdate2 = etronikaMambuClientUpdateMapper
					.toMambuClientFamilyDetails(
							etronikaMambuClientUpdateMapper.toFamilyDetailsObject(etronikaLoanRequest));
			MambuClientDetailsUpdate mambuClientDetailsUpdate3 = etronikaMambuClientUpdateMapper
					.toMambuClientLiabilitiesDetails(
							etronikaMambuClientUpdateMapper.toLiabilitiesDetailsObject(etronikaLoanRequest));

			MambuClientDetailsUpdate[] mambuClientDetailsUpdates = { mambuClientDetailsUpdate1,
					mambuClientDetailsUpdate2, mambuClientDetailsUpdate3 };
			return mambuClientDetailsUpdateService.updateCustomerDetails(mambuClientDetailsUpdates,
					etronikaLoanRequest.getIdentifier());
		}).map(response1 -> {
			return new EtronikaSuccessResponse("SUCCESS");
		}).thenReturn(new EtronikaSuccessResponse("SUCCESS"));
	}

	@Override
	public EtronikaLoanListResponse getLoanList(EtronikaLoanListRequest etronikaLoanListRequest) {

		MambuLoanResponse[] mambuLoansListRespone = mambuLoansListService
				.loansList(etronikaLoanListRequest.getCustomerId()).block();

		EtronikaLoanListResponse etronikaLoanListResponse = new EtronikaLoanListResponse();
		LoanListResponse loanListResponse = new LoanListResponse();
		List<Loan> loans = new ArrayList<>();
		for (MambuLoanResponse mambuLoanResponse : mambuLoansListRespone) {
			if (mambuLoanResponse.getAccountState().equalsIgnoreCase("ACTIVE")
					|| mambuLoanResponse.getAccountState().equalsIgnoreCase("CLOSED")
					|| mambuLoanResponse.getAccountState().equalsIgnoreCase("ACTIVE_IN_ARREARS")) {
				MambuSchedule mambuSchedule = loanRepaymentScheduleService.repaymentSchedule(mambuLoanResponse.getId())
						.block();
				OffsetDateTime lastPaymentDate = null;
				for (int i = 0; i < mambuSchedule.getInstallments().size(); i++) {
					if (mambuSchedule.getInstallments().get(i).getLastPaidDate() != null)
						lastPaymentDate = mambuSchedule.getInstallments().get(i).getLastPaidDate();
					else
						break;
				}

				Loan loan;

				loan = mambuLoansListService.mambuEtronikaMapper(mambuLoanResponse);
				for (int i = 0; i < mambuSchedule.getInstallments().size(); i++) {
					if (mambuSchedule.getInstallments().get(i).getRepaidDate() == null) {
						loan.setDateNextPayment(mambuSchedule.getInstallments().get(i).getDueDate());
						break;
					}
				}
				Balances balances = mambuLoanResponse.getBalances();
				double balance = balances.getInterestBalance() + balances.getInterestPaid()
						+ balances.getPrincipalBalance() + balances.getPrincipalPaid();
				List<Schedule> schedules = loanRepaymentScheduleService.getEtronikaSchedule(mambuSchedule, balance);
				List<Payment> payments = loanRepaymentScheduleService.getEtronikaNextPaymentSchedule(mambuSchedule,
						lastPaymentDate);
				loan.setPayments(payments);
				loan.setSchedule(schedules);
				if(etronikaLoanListRequest.getAgreementId() != null && !etronikaLoanListRequest.getAgreementId().isEmpty())	{
					if(loan.getId().equalsIgnoreCase(etronikaLoanListRequest.getAgreementId()))	{
						loans.add(loan);
					}
				}
				else	{
					loans.add(loan);
				}
			} else {
				log.info("Ignored loan account because not approved: " + mambuLoanResponse.getId());
			}
		}
		loanListResponse.setLoans(loans);
		etronikaLoanListResponse.setResponse(loanListResponse);
		etronikaLoanListResponse.setStatus("SUCCESS");
		return etronikaLoanListResponse;
	}
}
