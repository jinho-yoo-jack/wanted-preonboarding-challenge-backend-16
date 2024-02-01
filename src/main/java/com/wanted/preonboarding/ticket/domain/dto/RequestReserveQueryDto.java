package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class RequestReserveQueryDto {
    private String reservationName;
    private String reservationPhoneNumber;
}
