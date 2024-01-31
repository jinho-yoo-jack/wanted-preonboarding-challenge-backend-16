package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RequiredArgsConstructor
@SpringBootTest
class ReserveControllerTest {

    @Autowired
    TicketSeller ticketSeller;

    @Test
    public void reservation(){

        boolean result = ticketSeller.reserve(ReserveInfo.builder()
                .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .reservationName("유진호")
                .reservationPhoneNumber("010-1234-1234")
                .reservationStatus("reserve")
                .amount(200000)
                .round(1)
                .line('A')
                .seat(1)
                .build());

        assertThat(result);
    }
}