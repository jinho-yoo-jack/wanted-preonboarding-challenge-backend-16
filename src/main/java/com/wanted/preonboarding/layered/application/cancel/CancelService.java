package com.wanted.preonboarding.layered.application.cancel;

import com.wanted.preonboarding.layered.application.ticketing.v3.TicketOffice;
import com.wanted.preonboarding.layered.domain.dto.ReservationInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CancelService {
    private final TicketOffice ticketOffice;

    public boolean cancelTicket() {
        // 예약한 티켓 정보를 받아야 한다.
        // 필요한 요소 : performance_id, round, `line`, seat, userName, userPhoneNumber
        ReservationInfo message = ReservationInfo.builder()
            .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
            .round(1)
            .gate(1)
            .line('A')
            .seat(1)
            .userName("유진호")
            .phoneNumber("010-1234-1234")
            .build();
        log.info("message => {}", message.getPerformanceId());
        ticketOffice.ticketCancelBy(message);

        return false;
    }
}
