package com.wanted.preonboarding.ticket.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationInquiryDto {

    private UUID performanceId;
    private String performanceName;
    private int round;
    private String line;
    private int seat;
    private LocalDateTime startDate;
    private String reservationHolderName;
    private String reservationHolderPhoneNumber;
}
