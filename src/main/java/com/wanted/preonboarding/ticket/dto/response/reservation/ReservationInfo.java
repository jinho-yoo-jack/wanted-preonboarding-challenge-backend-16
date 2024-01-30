package com.wanted.preonboarding.ticket.dto.response.reservation;

import com.wanted.preonboarding.ticket.domain.performance.Performance;
import com.wanted.preonboarding.ticket.domain.performance.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.reservation.Reservation;
import lombok.Builder;

/*
* 예약 정보
* 예매가 완료된 공연의 정보(회차, 공연명, 좌석정보, 공연ID) + 예매자 정보(이름, 연락처) + 결제 가격
* */
@Builder
public record ReservationInfo(
    String performanceName,
    int performanceRound,
    String performanceId,
    String line,
    int seat,
    String name,
    String phone,
    int price

) {

    public static ReservationInfo of(Reservation reservation, PerformanceSeatInfo performanceSeatInfo, String performanceName) {
        return ReservationInfo.builder()
            .performanceName(performanceName)
            .performanceRound(performanceSeatInfo.getRound())
            .performanceId(performanceSeatInfo.getPerformanceId().toString())
            .line(performanceSeatInfo.getLine())
            .seat(performanceSeatInfo.getSeat())
            .name(reservation.getName())
            .phone(reservation.getPhoneNumber())
            .build();
    }
}
