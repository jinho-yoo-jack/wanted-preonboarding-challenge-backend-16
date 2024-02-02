package com.wanted.preonboarding.common.exception;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * 익셉션 별
 * 
 */
@ControllerAdvice
@RestController
public class ExceptionAdvisor {
	
	@Autowired
	private ExceptionService exceptionService;
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> processValidationError(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest().body(exceptionService.selectMethodArgumentNotValidExceptionMessage(exception));
    }
}
