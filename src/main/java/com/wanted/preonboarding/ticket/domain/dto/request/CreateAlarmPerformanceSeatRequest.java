package com.wanted.preonboarding.ticket.domain.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@Builder
public class CreateAlarmPerformanceSeatRequest {
    private UUID performanceId; //공연ID
    private String reservationName; //이름
    private String reservationPhoneNumber; //연락처
    private String reservationEmail; //이메일
}
