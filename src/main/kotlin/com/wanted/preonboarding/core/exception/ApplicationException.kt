package com.wanted.preonboarding.core.exception

import org.springframework.http.HttpStatus

sealed class ApplicationException(
    override val message: String,
    val httpStatus: HttpStatus,
) : RuntimeException(message) {
    class NotFoundException(message: String) : ApplicationException(message, HttpStatus.NOT_FOUND)

    class BadRequestException(message: String) : ApplicationException(message, HttpStatus.BAD_REQUEST)
}
