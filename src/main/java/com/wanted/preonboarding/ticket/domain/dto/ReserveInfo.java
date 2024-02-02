package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.global.dto.BaseResDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;

@Getter @Setter
@Builder
public class ReserveInfo {
    // 공연 및 전시 정보 + 예약자 정보
    private UUID performanceId; // 예약을 원하는 공연 또는 전시회ID
    private String reservationName; // 고객의 이름
    private String reservationPhoneNumber; // 휴대 전화
    private String reservationStatus; // 예약; 취소;
    private long amount; // 결제 가능한 금액(잔고)
    private int gate;
    private int round; // 회차
    private char line; // 좌석 정보
    private int seat; // 좌석 정보
}
