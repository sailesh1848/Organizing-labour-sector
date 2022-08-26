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
import com.capg.bcm.loanorigination.dto.mambu.ApplyFee;

/**
 * @author csailesh
 *
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApplyFeeMapper {

	/**
	 * @param etronikaLoanRequest
	 * @return ApplyFee
	 */
	@Mapping(target = "amount", source = "etronikaLoanRequest.administrationFee")
	@Mapping(target = "externalId", ignore = true)
	@Mapping(target = "notes", source = "etronikaLoanRequest.identifier")
	ApplyFee toApplyFee(EtronikaLoanRequest etronikaLoanRequest);

	/**
	 * @param applyFee
	 * @param etronikaLoanRequest
	 */
	@AfterMapping
	default void setValueDate(@MappingTarget ApplyFee applyFee, EtronikaLoanRequest etronikaLoanRequest) {

		LocalDateTime now = LocalDateTime.now(ZoneOffset.ofOffset("UTC", ZoneOffset.ofHours(3)));
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+03:00");
		applyFee.setValueDate(now.format(format));
		applyFee.setInstallmentNumber(1);

	}

}
