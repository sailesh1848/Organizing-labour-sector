package com.capg.bcm.loanorigination.service;

import com.capg.bcm.loanorigination.dto.EtronikaLoanRequest;
import com.capg.bcm.loanorigination.dto.MambuApproveRequest;
import com.capg.bcm.loanorigination.dto.MambuClientResponse;
import com.capg.bcm.loanorigination.dto.MambuLoanAccountRequest;
import com.capg.bcm.loanorigination.dto.mambu.MambuLoanResponse;

import reactor.core.publisher.Mono;

/**
 * @author durgeshs
 *
 */
public interface LoanAccountCreationService {

	/**
	 * This method is used for creation of loan in Mambu
	 * 
	 * @param {@link MambuLoanAccountRequest}
	 * @return {@link MambuLoanResponse}
	 *
	 */
	public Mono<MambuLoanResponse> createLoan(MambuLoanAccountRequest mambuLoanAccountRequest);

	/**
	 * 
	 * This method is used to get the client Encoded key
	 * 
	 * @param {@link EtronikaLoanRequest}
	 * @return {@link MambuClientResponse}
	 *
	 */
	public Mono<MambuClientResponse> getClient(EtronikaLoanRequest etronikaLoanRequest);

	/**
	 * This method is used for approving the loan account
	 * 
	 * @param loanAccountId, {@link MambuApproveRequest}
	 * @return {@link MambuLoanResponse}
	 *
	 */
	public Mono<MambuLoanResponse> approveRequest(String loanAccountId, MambuApproveRequest mambuApproveRequest);

	/**
	 * 
	 * This method is used to generate the {@link MambuApproveRequest} bean.
	 * 
	 * @return {@link MambuApproveRequest}
	 *
	 */
	MambuApproveRequest toMambuApproveRequest();

}
