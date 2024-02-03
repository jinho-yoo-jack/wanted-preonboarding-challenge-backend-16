package com.wanted.preonboarding.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ResponseWrapper {

	private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

	private String path;

	private String status;

	private Boolean isSuccess;

	private String message;

	private Object responseData;

	public ResponseWrapper(HttpServletRequest req, HttpStatus status, Boolean isSuccess, String message,
		Object responseData) {
		this.path = req.getRequestURI() + " [" + req.getMethod() + "]";
		this.status = status.toString();
		this.isSuccess = isSuccess;
		this.message = message;
		this.responseData = responseData;
	}
}
