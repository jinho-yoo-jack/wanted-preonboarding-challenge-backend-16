package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestReserveQueryDto {
    private String reservationName;
    private String reservationPhoneNumber;
}
