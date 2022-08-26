package com.capg.bcm.loanorigination.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MambuError {

	private List<ErrorBean> errors;

}
