package com.wanted.preonboarding.ticket.domain.mapper;

import java.util.UUID;

public interface ReservationMapping {

    // 공연ID
    UUID getPerformanceId();
    // 공연명
    String getPerformanceName();
    // 회차
    int getRound();
    // 좌석
    int getSeat();
    // 예매자명
    String getName();
    // 연락처
    String getPhoneNumber();
}
