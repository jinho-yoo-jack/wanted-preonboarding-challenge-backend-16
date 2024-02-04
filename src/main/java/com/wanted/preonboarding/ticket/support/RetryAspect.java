package com.wanted.preonboarding.ticket.support;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object retry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        int maxRetry = retry.time();
        Exception exceptionHolder = null;

        for (int count = 1; count < maxRetry; count++) {
            try {
                return joinPoint.proceed();
            } catch (Exception exception) {
                log.warn("[retry] Exception has occurred (count: {})", count);
                exceptionHolder = exception;
            }
        }
        throw Objects.requireNonNull(exceptionHolder);
    }
}
