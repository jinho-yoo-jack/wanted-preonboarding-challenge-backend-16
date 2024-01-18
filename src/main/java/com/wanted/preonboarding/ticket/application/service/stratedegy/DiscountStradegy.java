package com.wanted.preonboarding.ticket.application.service.stratedegy;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;

public interface DiscountStradegy {

    int caculateDiscount(Reservation reservation);

    String getName();
}
