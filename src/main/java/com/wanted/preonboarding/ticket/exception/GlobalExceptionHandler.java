package com.wanted.preonboarding.ticket.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException exception){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder().exceptionMessage(exception.getMessage()).httpStatus(HttpStatus.OK).build();
        return new ResponseEntity<>(exceptionResponse, exceptionResponse.httpStatus);
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponse> NoSuchElementException(NoSuchElementException exception){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder().exceptionMessage(exception.getMessage() ).httpStatus(HttpStatus.NO_CONTENT).build();
        return new ResponseEntity<>(exceptionResponse, exceptionResponse.httpStatus);
    }


    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ExceptionResponse> EmptyResultDataAccessException(EmptyResultDataAccessException exception){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder().exceptionMessage(exception.getMessage() ).httpStatus(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<>(exceptionResponse, exceptionResponse.httpStatus);
    }
}
