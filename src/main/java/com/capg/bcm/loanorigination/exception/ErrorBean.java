package com.capg.bcm.loanorigination.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorBean {
	private int errorCode;
	private String errorReason;
	private String errorSource;
}
