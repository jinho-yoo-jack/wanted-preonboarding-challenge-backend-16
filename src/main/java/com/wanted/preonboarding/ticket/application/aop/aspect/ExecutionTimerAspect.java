package com.wanted.preonboarding.ticket.application.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class ExecutionTimerAspect {

    @Around("@annotation(com.wanted.preonboarding.ticket.application.aop.annotation.ExecutionTimer)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        String methodSignature = joinPoint.getSignature().getName();
        try {
            stopWatch.start();
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Exception occurred while measuring execution time of method: {}", methodSignature);
            throw throwable;
        } finally {
            stopWatch.stop();
            log.info("{} 실행 시간 : {} ms", methodSignature, stopWatch.getTotalTimeMillis());
        }
    }

}

