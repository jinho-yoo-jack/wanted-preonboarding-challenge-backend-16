package com.wanted.preonboarding.ticket.application.service.strategy;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EarlyBirdDiscountStrategy implements DiscountStrategy {

    // Clock 적용 해보기
    public static final String NAME = "얼리버드 할인";
    public static final int EARLY_BIRD_MONTH = 3;
    public static final int EARLY_BIRD_DISCOUNT = 15;
    @Override
    public int caculateDiscount(Reservation reservation) {
        int price = reservation.getPerformanceSeatInfo().getPerformance().getPrice();
        if (isEarlyBird(reservation)) {
            price = (int) (price * (1 - (EARLY_BIRD_DISCOUNT / 100.0)));
        }
        return price;
    }

    @Override
    public String getName() {
        return NAME;
    }

    private boolean isEarlyBird(Reservation reservation) {
        LocalDateTime performanceDate = reservation.getPerformanceSeatInfo().getPerformance().getStartDate();
        LocalDateTime reservationDate = LocalDateTime.now();
        return performanceDate.minusMonths(EARLY_BIRD_MONTH).isBefore(reservationDate);
    }
}
