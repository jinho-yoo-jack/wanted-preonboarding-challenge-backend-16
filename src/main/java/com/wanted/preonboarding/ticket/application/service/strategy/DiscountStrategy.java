package com.wanted.preonboarding.ticket.application.service.strategy;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;

public interface DiscountStrategy {

    int caculateDiscount(Reservation reservation);

    String getName();
}
