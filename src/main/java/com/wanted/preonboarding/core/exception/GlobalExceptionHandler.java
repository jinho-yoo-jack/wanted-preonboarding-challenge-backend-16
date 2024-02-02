package com.wanted.preonboarding.core.exception;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ResponseHandler> methodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException ex) {

		log.warn(ex.getMessage());

		String message = "잘못된 parameter 입니다. 문서를 참고하여 올바른 데이터를 첨부해주세요.";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(
				ResponseHandler
					.builder()
					.message(message)
					.statusCode(HttpStatus.BAD_REQUEST)
					.build());
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ResponseHandler> entityNotFoundException(
		EntityNotFoundException ex) {

		log.warn(ex.getMessage());
		String message = "잘못된 ID입니다. 해당하는 개체를 찾을 수 없습니다.";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(
				ResponseHandler
					.builder()
					.message(message)
					.statusCode(HttpStatus.BAD_REQUEST)
					.build());
	}
}
