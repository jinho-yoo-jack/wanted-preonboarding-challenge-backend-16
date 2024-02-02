package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@Builder
public class RequestReserveQueryDto {
    private String reservationName;
    private String reservationPhoneNumber;
}
