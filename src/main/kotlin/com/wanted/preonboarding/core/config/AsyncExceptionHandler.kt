package com.wanted.preonboarding.core.config

import com.wanted.preonboarding.core.exception.ApplicationException
import org.slf4j.LoggerFactory
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import java.lang.reflect.Method

class AsyncExceptionHandler : AsyncUncaughtExceptionHandler {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun handleUncaughtException(
        ex: Throwable,
        method: Method,
        vararg params: Any?,
    ) {
        when (ex) {
            is ApplicationException -> log.error("ApplicationException: ${ex.message}", ex)
            else -> log.error("Unexpected async exception: ${ex.message}", ex)
        }
    }
}
