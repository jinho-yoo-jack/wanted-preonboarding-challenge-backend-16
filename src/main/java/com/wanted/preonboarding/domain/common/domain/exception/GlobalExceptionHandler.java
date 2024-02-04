package com.wanted.preonboarding.domain.common.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wanted.preonboarding.domain.common.domain.response.ResponseHandler;
import com.wanted.preonboarding.domain.common.utils.ResponseUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ResponseHandler<Void>> handleException(Exception e) {

		log.error("Exception : " + e);
		return ResponseUtils.resultError(e.getMessage());
	}


}