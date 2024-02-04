package com.wanted.preonboarding.domain.common.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wanted.preonboarding.domain.common.domain.response.ResponseHandler;

public class ResponseUtils {

	public static ResponseEntity<ResponseHandler<Void>> resultOk() {
		return new ResponseEntity<>(
			setResponseHandler(
				HttpStatus.OK,
				null,
				null),
			HttpStatus.OK);
	}

	public static <T> ResponseEntity<ResponseHandler<T>> resultOk(T data) {
		return new ResponseEntity<>(
			setResponseHandler(
				HttpStatus.OK,
				null,
				data),
			HttpStatus.OK);
	}

	public static <T> ResponseEntity<ResponseHandler<Void>> resultError(String message) {

		return new ResponseEntity<>(
			setResponseHandler(
				HttpStatus.BAD_REQUEST,
				message,
				null),
			HttpStatus.BAD_REQUEST);
	}


	private static <T> ResponseHandler<T> setResponseHandler(
		HttpStatus status,
		String message,
		T data) {

		return ResponseHandler.<T>builder()
			.statusCode(status)
			.message(message)
			.data(data)
			.build();
	}
}
