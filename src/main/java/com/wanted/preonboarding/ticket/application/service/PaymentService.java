package com.wanted.preonboarding.ticket.application.service;

import com.wanted.preonboarding.ticket.application.service.stratedegy.DiscountStradegy;
import com.wanted.preonboarding.ticket.domain.dto.PaymentResponse;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    // 책임 : 결제 진행 & 취소 처리 (할인 적용 포함)
    private final List<DiscountStradegy> discountStradegies;

    public PaymentResponse processPayment(Reservation reservation, int balance) {
        log.info("--- Process Payment ---");
        Performance performance = reservation.getPerformanceSeatInfo().getPerformance();
        List<String> discountDetails = new ArrayList<>();
        int price = performance.getPrice();

        for (DiscountStradegy discountStradegy : discountStradegies) {
            price = discountStradegy.caculateDiscount(reservation);
            discountDetails.add(discountStradegy.getName());
        }

        return PaymentResponse.builder()
                .paidPrice(price)
                .discountDetails(discountDetails)
                .remainBalance(caculateRemainBalance(balance, price))
                .build();
    }

    private int caculateRemainBalance(int balance, int discountedPrice) {
        if (balance < discountedPrice) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
        return balance - discountedPrice;
    }


}
