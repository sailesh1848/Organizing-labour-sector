package com.capg.bcm.loanorigination.mapping;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.capg.bcm.loanorigination.dto.EtronikaLoanRequest;
import com.capg.bcm.loanorigination.dto.MambuClientResponse;
import com.capg.bcm.loanorigination.dto.MambuLoanAccountRequest;
import com.capg.bcm.loanorigination.dto.ScheduleSettings;

/**
 * @author durgeshs
 *
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MambuLoanRequestMapper {

	/**
	 * This method is used in the mapping of {@link MambuLoanAccountRequest} from
	 * {@link EtronikaLoanRequest} and {@link MambuClientResponse}
	 * 
	 * @param etronikaLoanRequest
	 * @param mambuClientResponse
	 * @return {@link MambuLoanAccountRequest}
	 */
	@Mapping(target = "accountHolderKey", source = "mambuClientResponse.encodedKey")
	@Mapping(target = "productTypeKey", constant = "8a8e87eb79e98c5b0179e9dba0b501dc")
	@Mapping(target = "accountHolderType", constant = "CLIENT")
	@Mapping(target = "loanAmount", source = "etronikaLoanRequest.amount")
	@Mapping(target = "interestSettings.interestRate", source = "etronikaLoanRequest.interestRate")
	@Mapping(target = "scheduleSettings.repaymentInstallments", source = "etronikaLoanRequest.term")
	@Mapping(target = "scheduleSettings.gracePeriod", constant = "0")
	MambuLoanAccountRequest toMambuLoanAccountRequest(EtronikaLoanRequest etronikaLoanRequest,
			MambuClientResponse mambuClientResponse);

	/**
	 * This method is used to add list of Fixed Days of month
	 * 
	 * @param mambuLoanAccountRequest
	 */
	@AfterMapping
	default void addFixedDays(@MappingTarget MambuLoanAccountRequest mambuLoanAccountRequest) {

		ScheduleSettings scheduleSettings = mambuLoanAccountRequest.getScheduleSettings();
		scheduleSettings.setFixedDaysOfMonth(List.of(30));
		mambuLoanAccountRequest.setScheduleSettings(scheduleSettings);
	}

}
