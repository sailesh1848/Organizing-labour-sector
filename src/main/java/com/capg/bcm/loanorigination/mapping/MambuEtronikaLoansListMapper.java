package com.capg.bcm.loanorigination.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MambuEtronikaLoansListMapper {

}
