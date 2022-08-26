package com.capg.bcm.loanorigination.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.capg.bcm.loanorigination.dto.EtronikaLoanListRequest;
import com.capg.bcm.loanorigination.dto.EtronikaLoanListResponse;
import com.capg.bcm.loanorigination.dto.EtronikaLoanRequest;
import com.capg.bcm.loanorigination.dto.EtronikaSuccessResponse;
import com.capg.bcm.loanorigination.exception.ExceptionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

/**
 * @author durgeshs
 *
 */
@Tag(name = "Loan Origination", description = "Etronika Loan Origination API")
public interface LoanOriginationController {

	/**
	 * This method is used to create the loan account in Mambu using loan
	 * origination journey
	 * 
	 * @param etronikaLoanRequest
	 * @return {@link EtronikaSuccessResponse}
	 */
	@Operation(summary = "Create Loan Account and Disbursement")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Success", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = EtronikaSuccessResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)) }) })
	@PostMapping(path = "/capgemini/api/v1.0.0/loans", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<EtronikaSuccessResponse> createLoan(@RequestBody EtronikaLoanRequest etronikaLoanRequest);

	@Operation(summary = "Get Loan List from Mambu")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Success", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = EtronikaLoanListResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)) }) })
	@PostMapping(path = "/loans", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public EtronikaLoanListResponse getLoanList(@RequestBody EtronikaLoanListRequest etronikaLoanListRequest);

}
