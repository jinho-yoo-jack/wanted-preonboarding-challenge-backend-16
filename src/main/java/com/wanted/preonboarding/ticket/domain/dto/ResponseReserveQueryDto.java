package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.global.dto.BaseResDto;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@Builder
public class ResponseReserveQueryDto extends BaseResDto {

    private UUID performanceId; //공연ID
    private int round; //회차
    private String performanceName; // 공연명
    private int gate; //좌석정보
    private char line; //좌석정보
    private int seat; //좌석정보
    private String reservationName; //이름
    private String reservationPhoneNumber; //연락처

    public static ResponseReserveQueryDto of(Reservation entity) {
        return ResponseReserveQueryDto.builder()
                .performanceId(entity.getPerformanceId())
                .round(entity.getRound())
                .gate(entity.getGate())
                .line(entity.getLine())
                .seat(entity.getSeat())
                .reservationName(entity.getName())
                .reservationPhoneNumber(entity.getPhoneNumber())
                .build();
    }
}
