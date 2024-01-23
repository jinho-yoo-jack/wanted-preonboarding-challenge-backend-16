package com.wanted.preonboarding.ticket.presentation.handler

import com.wanted.preonboarding.ticket.presentation.common.ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(e: RuntimeException): ApiResponse<Unit> {
        logger.warn("RuntimeException: ${e.message}", e)
        return ApiResponse.fail(e.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ApiResponse<Unit> {
        logger.warn("Unexpected exception: ${e.message}", e)
        return ApiResponse.error(e.message)
    }
}
