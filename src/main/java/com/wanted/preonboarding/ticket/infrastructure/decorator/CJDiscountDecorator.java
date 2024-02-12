package com.wanted.preonboarding.ticket.infrastructure.decorator;

import com.wanted.preonboarding.ticket.domain.code.DiscountType;
import com.wanted.preonboarding.ticket.domain.decorator.DiscountPaymentDecorator;
import com.wanted.preonboarding.ticket.domain.decorator.Payment;

public class CJDiscountDecorator extends DiscountPaymentDecorator {

    public CJDiscountDecorator(Payment payment) {
        super(payment);
    }

    @Override
    public DiscountType getDiscountType() {
        return DiscountType.CJ;
    }
}
