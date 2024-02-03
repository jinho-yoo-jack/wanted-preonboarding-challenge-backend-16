package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/performance")
@RequiredArgsConstructor
public class PerformanceController {
    private final TicketSeller ticketSeller;

    @PostMapping("/")
    public List<ResponseReserveInfo> createdPerformance() throws Exception {
        List<String> seats = Arrays.asList("A1", "A2");
        ReserveInfo reserveInfo = ReserveInfo.builder()
                .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .reservationName("유진호")
                .reservationPhoneNumber("010-1234-1234")
                .reservationStatus("reserve")
                .amount(200000)
                .round(1)
                .seats(seats)
                .build();

        return ticketSeller.reserve(reserveInfo);
    }
    @GetMapping("/")
    public PerformanceInfo getPerformanceDetail(String performanceName){
        return ticketSeller.getPerformanceInfoDetail(performanceName);
    }
    @GetMapping("/reserve")
    public List<ResponseReserveInfo> getReserveInfo(GetReservationRequestDto requestDto){
        return ticketSeller.getReserveInfo(requestDto);
    }

    @PatchMapping("/")
    public void cancelReservation(CancelReservationRequestDto requestDto) {
        ticketSeller.cancelReservation(requestDto);
    }


}
