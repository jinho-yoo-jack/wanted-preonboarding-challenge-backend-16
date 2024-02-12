package com.wanted.preonboarding.ticket.infrastructure.decorator;

import com.wanted.preonboarding.core.code.DiscountType;
import com.wanted.preonboarding.ticket.domain.decorator.DiscountPaymentDecorator;
import com.wanted.preonboarding.ticket.domain.decorator.Payment;

public class SKTDiscountDecorator extends DiscountPaymentDecorator {

    public SKTDiscountDecorator(Payment payment) {
        super(payment);
    }

    @Override
    public DiscountType getDiscountType() {
        return DiscountType.SKT;
    }
}
