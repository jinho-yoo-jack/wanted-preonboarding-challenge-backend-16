package com.wanted.preonboarding.ticket.application.ticket;

import com.wanted.preonboarding.ticket.application.discount.DiscountService;
import com.wanted.preonboarding.ticket.application.discount.DiscountServiceFactory;
import com.wanted.preonboarding.ticket.domain.dto.request.PaymentRequest;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.exception.exceptions.PayPriceIsNotEnoughException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {
    private final DiscountServiceFactory discountServiceFactory;

    @Transactional
    public void payReservation(PaymentRequest paymentRequest, Performance performance)
    {
        DiscountService discountService = discountServiceFactory.getTypeOf(paymentRequest.getDiscountType());
        int restAmount = paymentRequest.getAmount() - discountService.calculatePrice(performance.getPrice());
        if(restAmount < 0) throw new PayPriceIsNotEnoughException();
    }
}
