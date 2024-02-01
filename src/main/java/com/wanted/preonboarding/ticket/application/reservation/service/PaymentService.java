package com.wanted.preonboarding.ticket.application.reservation.service;

import com.wanted.preonboarding.ticket.application.common.exception.PaymentFailedException;
import com.wanted.preonboarding.ticket.application.reservation.service.strategy.DiscountStrategy;
import com.wanted.preonboarding.ticket.domain.dto.response.PaymentResponse;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.wanted.preonboarding.ticket.application.common.exception.ExceptionStatus.PAYMENT_FAILED_INSUFFICIENT_BALANCE;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    // 책임 : 결제 진행 & 취소 처리 (할인 적용 포함)
    private final List<DiscountStrategy> discountStradegies;

    public PaymentResponse processPayment(Reservation reservation, int balance) {
        log.info("--- Process Payment ---");
        Performance performance = reservation.getPerformanceSeatInfo().getPerformance();
        List<String> discountDetails = new ArrayList<>();
        int price = performance.getPrice();

        for (DiscountStrategy discountStrategy : discountStradegies) {
            price = discountStrategy.caculateDiscount(reservation);
            discountDetails.add(discountStrategy.getName());
        }

        return createPaymentResponse(price, discountDetails, caculateRemainBalance(balance, price));
    }

    private int caculateRemainBalance(int balance, int discountedPrice) {
        if (balance < discountedPrice) {
            throw new PaymentFailedException(PAYMENT_FAILED_INSUFFICIENT_BALANCE);
        }
        return balance - discountedPrice;
    }

    private PaymentResponse createPaymentResponse(int price, List<String> discountDetails, int remainBalance) {
        return PaymentResponse.builder()
                .paidPrice(price)
                .discountDetails(discountDetails)
                .remainBalance(remainBalance)
                .build();
    }

}
