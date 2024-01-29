package com.wanted.preonboarding.ticket.application.discount;

import com.wanted.preonboarding.ticket.dto.request.discount.PaymentInfo;

public interface DiscountManager {
    double applyDiscount(PaymentInfo paymentInfo);

}
