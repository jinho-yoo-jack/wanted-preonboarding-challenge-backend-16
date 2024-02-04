package com.wanted.preonboarding.ticket.domain.dto.response;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.UUID;

@Data
@Builder
public class ReserveInfoResponse {
    private UUID performanceId;
    private String performanceName;
    private String reservationStatus;
    private SeatInfoResponse seatInfoResponse;
    private ReserverInfoResponse reserverInfoResponse;
}
