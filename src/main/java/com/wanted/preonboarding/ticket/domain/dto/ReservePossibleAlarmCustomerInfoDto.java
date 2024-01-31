package com.wanted.preonboarding.ticket.domain.dto;


import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ReservePossibleAlarmCustomerInfoDto {
    private UUID performanceId; //공연ID
    private String reservationName; //이름
    private String reservationPhoneNumber; //연락처
}
