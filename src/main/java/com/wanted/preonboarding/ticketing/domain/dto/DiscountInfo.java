package com.wanted.preonboarding.ticketing.domain.dto;

import com.wanted.preonboarding.ticketing.domain.dto.request.CreateReservationRequest;
import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DiscountInfo {
    private LocalDateTime startDate;
    private int balance;
    private int performancePrice;
    private Boolean isSolider;

    public static DiscountInfo of(Performance performance, CreateReservationRequest createReservationRequest) {
        return DiscountInfo.builder()
                .startDate(performance.getStartDate())
                .balance(createReservationRequest.getBalance())
                .performancePrice(performance.getPrice())
                .isSolider(createReservationRequest.getIsSolider())
                .build();
    }

    public boolean isAfter(LocalDateTime oneWeekLater) {
        return this.startDate.isAfter(oneWeekLater);
    }

    public int calculateDiscountAmount(int discountPercent) {
        int discountedPerformancePrice = this.performancePrice - (this.performancePrice * discountPercent / 100);

        return this.balance - discountedPerformancePrice;
    }

}
