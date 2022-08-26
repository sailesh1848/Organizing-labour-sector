package com.capg.bcm.loanorigination.service;

import java.time.OffsetDateTime;
import java.util.List;

import com.capg.bcm.loanorigination.dto.MambuSchedule;
import com.capg.bcm.loanorigination.dto.Payment;
import com.capg.bcm.loanorigination.dto.Schedule;

import reactor.core.publisher.Mono;

public interface LoanRepaymentScheduleService {

	public Mono<MambuSchedule> repaymentSchedule(String loanAccountId);

	public List<Schedule> getEtronikaSchedule(MambuSchedule mambuSchedule, double balance);

	public List<Payment> getEtronikaNextPaymentSchedule(MambuSchedule mambuSchedule, OffsetDateTime lastPaymentDate);

}
