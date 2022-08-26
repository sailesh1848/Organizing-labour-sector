package com.capg.bcm.loanorigination.service;

import com.capg.bcm.loanorigination.dto.Loan;
import com.capg.bcm.loanorigination.dto.mambu.MambuLoanResponse;

import reactor.core.publisher.Mono;

public interface MambuLoansListService {
	public Mono<MambuLoanResponse[]> loansList(String customerId);

	public Loan mambuEtronikaMapper(MambuLoanResponse mambuLoanResponse);
}
