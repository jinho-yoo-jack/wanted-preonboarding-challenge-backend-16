package com.wanted.preonboarding.ticket.domain.dto.assembler;

import com.wanted.preonboarding.ticket.domain.dto.request.ReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.*;
import com.wanted.preonboarding.ticket.domain.entity.*;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class ReservationAssembler {
    public Reservation toReservationEntity(ReservationRequest request, PerformanceSeatInfo performanceSeatInfo)
    {
        return Reservation.builder()
                .performanceId(UUID.fromString(request.getPerformanceId()))
                .reserverInfo(ReserverInfo.builder()
                        .name(request.getReserverInfoRequest().getName())
                        .phoneNumber(request.getReserverInfoRequest().getPhoneNumber()).build())
                .performanceSeatInfo(performanceSeatInfo)
                .build();
    }

    public ReserveInfoResponse toReserveInfoResponse(Reservation reservation,
                                                     PerformanceSeatInfo performanceSeatInfo,
                                                     Performance performance)
    {
        return ReserveInfoResponse.builder()
                .performanceId(performance.getId())
                .performanceName(performance.getName())
                .seatInfoResponse(toSeatInfoResponse(performanceSeatInfo))
                .reservationStatus("reserved")
                .reserverInfoResponse(toReserverInfoResponse(reservation.getReserverInfo()))
                .build();
    }

    public SeatInfoResponse toSeatInfoResponse(PerformanceSeatInfo seatInfo)
    {
        return SeatInfoResponse.builder()
                .performanceSeatInfoId(seatInfo.getId())
                .round(seatInfo.getSeatInfo().getRound())
                .line(seatInfo.getSeatInfo().getLine())
                .seat(seatInfo.getSeatInfo().getSeat())
                .gate(seatInfo.getSeatInfo().getGate())
                .build();
    }

    public static ReserverInfoResponse toReserverInfoResponse(ReserverInfo reserverInfo)
    {
        return ReserverInfoResponse.builder()
                .name(reserverInfo.getName())
                .phoneNumber(reserverInfo.getPhoneNumber())
                .build();
    }

}