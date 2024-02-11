package com.wanted.preonboarding.ticket.presentation;

import org.springframework.stereotype.Service;

@Service
public class ReserveStrategy {
    public void isReserveEnable(ReserveStrategyInterface reserveStrategyInterface, String isReserve) {
        reserveStrategyInterface.isReserveEnable(isReserve);
    }
}
