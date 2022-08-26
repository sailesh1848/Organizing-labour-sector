package com.capg.bcm.loanorigination.service;

import com.capg.bcm.loanorigination.dto.MambuClientDetailsUpdate;

import reactor.core.publisher.Mono;

public interface MambuClientDetailsUpdateService {

	public Mono<Void> updateCustomerDetails(MambuClientDetailsUpdate[] mambuClientDetailsUpdates, String clientId);

}
