package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.CancelReservationRequestDto;
import com.wanted.preonboarding.ticket.domain.dto.GetReservationRequestDto;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.dto.ResponseReserveInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
    private final TicketSeller ticketSeller;

    @PostMapping("/")
    public ResponseReserveInfo reservation() throws Exception {
        System.out.println("reservation");
        return ticketSeller.reserve(ReserveInfo.builder()
                .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .reservationName("유진호")
                .reservationPhoneNumber("010-1234-1234")
                .reservationStatus("reserve")
                .amount(200000)
                .round(1)
                .line('A')
                .seat(1)
                .build()
        );
    }
    @GetMapping("/")
    public List<ResponseReserveInfo> getState(GetReservationRequestDto requestDto){
        return ticketSeller.getReserveInfo(requestDto);
    }

    @PatchMapping("/")
    public void cancelReservation(CancelReservationRequestDto requestDto) {
        ticketSeller.cancelReservation(requestDto);
    }


}
