package com.wanted.preonboarding.ticketing.service;

import com.wanted.preonboarding.ticketing.aop.advice.exception.NotEnoughBalanceException;
import com.wanted.preonboarding.ticketing.aop.advice.payload.ErrorCode;
import com.wanted.preonboarding.ticketing.domain.dto.request.CreateReservationRequest;
import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import org.springframework.stereotype.Component;

@Component
public class ReservationValidator {
    public void validateBalance(CreateReservationRequest createReservationRequest, Performance performance) {
        if (!performance.isAffordable(createReservationRequest.getBalance())) {
            throw new NotEnoughBalanceException(ErrorCode.NOT_ENOUGH_BALANCE);
        }
    }
}