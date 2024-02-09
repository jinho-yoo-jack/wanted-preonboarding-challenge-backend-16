package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReserveResult {

    private UUID performanceId;
    private String performanceName;
    private int reservationId;
    private int round;
    private char line;
    private int seat;
    private int price;
    private String reservationName; // 예약자 이름
    private String phoneNumber; // 예약자 핸드폰 번호

    public static ReserveResult of(Reservation entity){
        return ReserveResult.builder()
            .performanceId(entity.getPerformance().getId())
            .performanceName(entity.getPerformance().getName())
            .reservationId(entity.getId())
            .round(entity.getPerformance().getRound())
            .line(entity.getLine())
            .seat(entity.getSeat())
            .price(entity.getPrice())
            .reservationName(entity.getUser().getName())
            .phoneNumber(entity.getUser().getPhoneNumber())
            .build();
    }

}
