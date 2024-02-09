package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.config.UserAuth;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.user.application.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReserveController {
    private final TicketSeller ticketSeller;
    private final UserInfoService userInfoService;

//    @PostMapping("/")
//    public boolean reservation() {
//        int reservationId = 0;
//        System.out.println("reservation");
//
//        return ticketSeller.reserve(ReserveInfo.builder()
//            .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
//            .reservationName("유진호")
//            .reservationStatus("reserve")
//            .balanceAmount(200000)
//            .round(1)
//            .line('A')
//            .seat(1)
//            .build()
//        , reservationId);
//    }


}
