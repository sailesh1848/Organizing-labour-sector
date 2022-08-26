package com.capg.bcm.loanorigination.service;

import com.capg.bcm.loanorigination.dto.ApplyFeeResponse;
import com.capg.bcm.loanorigination.dto.MambuDisbursementResponse;
import com.capg.bcm.loanorigination.dto.MambuMakeDisbursment;
import com.capg.bcm.loanorigination.dto.mambu.ApplyFee;

import reactor.core.publisher.Mono;

/**
 * @author csailesh
 *
 */
public interface DisbursementService {

	/**
	 * @param mambuMakeDisbursment
	 * @param accountId
	 * @return MambuDisbursementResponse
	 */
	Mono<MambuDisbursementResponse> initiateDisbursementbyId(MambuMakeDisbursment mambuMakeDisbursment,
			String accountId);

	/**
	 * @param applyFee
	 * @param accountId
	 * @return ApplyFeeResponse
	 */
	Mono<ApplyFeeResponse> applyFeeonDisbursementbyId(ApplyFee applyFee, String accountId);

}
