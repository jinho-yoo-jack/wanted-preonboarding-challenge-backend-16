package com.wanted.preonboarding.ticket.presentation.handler

import com.wanted.preonboarding.core.exception.ApplicationException
import com.wanted.preonboarding.ticket.presentation.common.ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(ApplicationException::class)
    fun handleRuntimeException(e: ApplicationException): ResponseEntity<ApiResponse<Unit>> {
        logger.warn("ApplicationException: ${e.message}", e)
        return ResponseEntity(ApiResponse.fail(e.message), e.httpStatus)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Unit>> {
        logger.warn("Unexpected exception: ${e.message}", e)
        return ResponseEntity(ApiResponse.error(e.message), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
