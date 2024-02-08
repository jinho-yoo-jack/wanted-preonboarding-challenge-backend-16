package com.wanted.preonboarding.ticket.presentation;

import org.springframework.stereotype.Service;

@Service
public class ReserveStrategy {
    public void isReserveEnable(ReserveStrategyInterface reserveStrategy, String isReserve) {
        reserveStrategy.isReserveEnable(isReserve);
    }
}
