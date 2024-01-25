package com.wanted.preonboarding.ticket.domain.dto.response;

import java.util.UUID;

public class ReserveResponse {
    private String performanceName; //공연명
    private int performanceRound;  //공연 회차
    private char line; //라인
    private int seat; //좌석 번호
    private UUID performanceId; //공연 아이디
    private String customerName; //예약자 이름
    private String customerPhoneNumber; //예약자 연락처
}
