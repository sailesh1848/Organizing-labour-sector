package com.capg.bcm.loanorigination.mapping;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.capg.bcm.loanorigination.dto.EtronikaLoanRequest;
import com.capg.bcm.loanorigination.dto.MambuMakeDisbursment;

/**
 * @author csailesh
 *
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MakeDisburesmentMapper {

	/**
	 * @param etronikaLoanRequest
	 * @return MambuMakeDisbursment
	 */
	@Mapping(target = "transferDetails.linkedAccountId", source = "etronikaLoanRequest.disbursementAccountNumber")
	@Mapping(target = "externalId", ignore = true)
	@Mapping(target = "notes", source = "etronikaLoanRequest.identifier")
	MambuMakeDisbursment toMambuMakeDisbursment(EtronikaLoanRequest etronikaLoanRequest);

	/**
	 * @param mambuMakeDisbursment
	 * @param etronikaLoanRequest
	 */
	@AfterMapping
	default void setValueDate(@MappingTarget MambuMakeDisbursment mambuMakeDisbursment,
			EtronikaLoanRequest etronikaLoanRequest) {

		LocalDateTime now = LocalDateTime.now(ZoneOffset.ofOffset("UTC", ZoneOffset.ofHours(3)));
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+03:00");
		mambuMakeDisbursment.setValueDate(now.format(format));

	}

}
