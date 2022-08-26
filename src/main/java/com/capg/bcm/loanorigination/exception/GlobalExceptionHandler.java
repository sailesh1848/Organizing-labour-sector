package com.capg.bcm.loanorigination.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	private static final String EXCEPTION_STRING = "Exception Occured :";
	private static final String MESSAGE = "ERROR";

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ExceptionResponse> resourceNotFound(ResourceNotFoundException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setStatus(MESSAGE);
		response.setErrorCode(HttpStatus.NOT_FOUND.toString());
		response.setErrorMessage(ex.getMessage());
		log.error(EXCEPTION_STRING + ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ExceptionResponse> badRequestException(BadRequestException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setStatus(MESSAGE);
		response.setErrorCode(HttpStatus.BAD_REQUEST.toString());
		response.setErrorMessage(ex.getMessage());
		log.error(EXCEPTION_STRING + ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InternalServerException.class)
	public ResponseEntity<ExceptionResponse> customException(InternalServerException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setStatus(MESSAGE);
		response.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		response.setErrorMessage(ex.getMessage());
		log.error(EXCEPTION_STRING + ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(WebClientResponseException.class)
	public ResponseEntity<ExceptionResponse> webClientException(WebClientResponseException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setStatus(MESSAGE);
		response.setErrorCode(String.valueOf(ex.getRawStatusCode()));
		response.setErrorMessage(ex.getMessage());
		log.error(EXCEPTION_STRING + ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ExceptionResponse> nullPointerException(NullPointerException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setStatus(MESSAGE);
		response.setErrorCode(HttpStatus.BAD_REQUEST.toString());
		response.setErrorMessage(ex.getMessage());
		log.error(EXCEPTION_STRING + ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}