package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationSearchResult {
    private int reservationId;
    private String performanceName;
    private int round;
    private char line;
    private int seat;
    private LocalDateTime startDate;
    private LocalDateTime createAt;

    public static ReservationSearchResult of(Reservation entity){
        return ReservationSearchResult.builder()
            .reservationId(entity.getId())
            .performanceName(entity.getPerformance().getName())
            .round(entity.getPerformance().getRound())
            .line(entity.getLine())
            .seat(entity.getSeat())
            .startDate(entity.getPerformance().getStart_date())
            .createAt(entity.getCreatedAt())
            .build();
    }

}
