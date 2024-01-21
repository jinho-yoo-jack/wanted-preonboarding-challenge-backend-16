package com.wanted.preonboarding.ticket.application.service.strategy;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EarlyBirdDiscountStrategy implements DiscountStrategy {

    public static final String NAME = "얼리버드 할인";
    public static final int MIN_MONTHS_BEFORE_PERFORMANCE_FOR_EARLY_BIRD = 3;
    public static final int EARLY_BIRD_DISCOUNT_RATE = 15;

    private final Clock clock;

    @Override
    public int caculateDiscount(Reservation reservation) {
        int originalPrice = reservation.getPerformanceSeatInfo().getPerformance().getPrice();
        if (isEligibleForEarlyBirdDiscount(reservation)) {
            return applyDiscount(originalPrice);
        }
        return originalPrice;
    }

    @Override
    public String getName() {
        return NAME;
    }

    private boolean isEligibleForEarlyBirdDiscount(Reservation reservation) {
        LocalDateTime performanceStartDate = reservation.getPerformanceSeatInfo().getPerformance().getStartDate();
        LocalDateTime currentDateTime = LocalDateTime.now(clock);

        return performanceStartDate.minusMonths(MIN_MONTHS_BEFORE_PERFORMANCE_FOR_EARLY_BIRD).isBefore(currentDateTime);
    }

    private int applyDiscount(int price) {
        return (int) (price * (1 - (EARLY_BIRD_DISCOUNT_RATE / 100.0)));
    }

}
