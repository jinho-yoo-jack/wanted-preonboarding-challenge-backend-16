package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.ticket.aop.StatusCode;
import com.wanted.preonboarding.ticket.aop.exception.ServiceException;
import com.wanted.preonboarding.ticket.global.common.ReserveStatus;
import org.springframework.stereotype.Service;

@Service
public class ConcreteEnableReserveStrategy implements ReserveStrategyInterface {

    @Override
    public void isReserveEnable(String isReserve) {
        if(!ReserveStatus.ENABLE.getValue().equalsIgnoreCase(isReserve)) {
            throw new ServiceException(StatusCode.RESERVE_NOT_VALID_PERFORMANCE_SEAT);
        }
    }
}
