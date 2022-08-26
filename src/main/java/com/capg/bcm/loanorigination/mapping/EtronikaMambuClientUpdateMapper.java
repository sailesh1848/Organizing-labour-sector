package com.capg.bcm.loanorigination.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.capg.bcm.loanorigination.dto.EmploymentDetailsObject;
import com.capg.bcm.loanorigination.dto.EtronikaLoanRequest;
import com.capg.bcm.loanorigination.dto.FamilyDetailsObject;
import com.capg.bcm.loanorigination.dto.LiabilitiesDetailsObject;
import com.capg.bcm.loanorigination.dto.MambuClientDetailsUpdate;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EtronikaMambuClientUpdateMapper {

	@Mapping(source = "childCount", target = "noOfChildrenClients")
	@Mapping(source = "maritalStatus", target = "maritalStatusClients")
	@Mapping(source = "familySize", target = "familySizeClients")
	FamilyDetailsObject toFamilyDetailsObject(EtronikaLoanRequest etronikaLoanRequest);

	@Mapping(source = "employerName", target = "employerNameClients")
	@Mapping(source = "employedFrom", target = "employmentStartDateClients", qualifiedByName = "dateFormat")
	@Mapping(source = "incomePerMonth", target = "incomePerMonthClients")
	EmploymentDetailsObject toEmploymentDetailsObject(EtronikaLoanRequest etronikaLoanRequest);

	@Mapping(source = "liabilitiesPerMonth", target = "liabilitiesPerMonthClients")
	@Mapping(source = "liabilitiesPerMonthConsumerLoan", target = "liabilitiesPerMonthForConsum")
	LiabilitiesDetailsObject toLiabilitiesDetailsObject(EtronikaLoanRequest etronikaLoanRequest);

	@Mapping(target = "op", constant = "ADD")
	@Mapping(target = "path", constant = "_Family_Details_Clients")
	@Mapping(target = "value", qualifiedByName = "familyDetails", source = "familyDetailsObject")
	MambuClientDetailsUpdate toMambuClientFamilyDetails(FamilyDetailsObject familyDetailsObject);

	@Mapping(target = "op", constant = "ADD")
	@Mapping(target = "path", constant = "_Employment_Details_Clients")
	@Mapping(target = "value", qualifiedByName = "employmentDetails", source = "employmentDetailsObject")
	MambuClientDetailsUpdate toMambuClientEmploymentDetails(EmploymentDetailsObject employmentDetailsObject);

	@Mapping(target = "op", constant = "ADD")
	@Mapping(target = "path", constant = "_Liabilities_Clients")
	@Mapping(target = "value", qualifiedByName = "liabilitiesDetails", source = "liabilitiesDetailsObject")
	MambuClientDetailsUpdate toMambuClientLiabilitiesDetails(LiabilitiesDetailsObject liabilitiesDetailsObject);

	@Named("familyDetails")
	default Object setFamilyDetails(FamilyDetailsObject familyDetailsObject) {
		return familyDetailsObject;
	}

	@Named("employmentDetails")
	default Object setEmploymentDetails(EmploymentDetailsObject employmentDetailsObject) {
		return employmentDetailsObject;
	}

	@Named("liabilitiesDetails")
	default Object setLiabilitiesDetails(LiabilitiesDetailsObject liabilitiesDetailsObject) {
		return liabilitiesDetailsObject;
	}

	@Named("dateFormat")
	default String setDateFormat(String callbackDate) {
		return callbackDate.split("T")[0] + "T00:00:00+03:00";
	}
}
