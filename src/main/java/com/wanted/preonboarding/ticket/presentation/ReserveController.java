package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 공연 예약과 관련된 정보를 처리하는 REST 컨트롤러로, /reserve 경로로 매핑되어있습니다.
 */
@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
  private final TicketSeller ticketSeller;

  /**
   * 예약을 실행한 후, 예약의 결과를 응답합니다.
   * POST 메서드로 '/'에서 요청을 받고, 예약의 결과를 반환합니다.
   *
   * @return 주어진 정보로 예약을 실행한 후, {@link TicketSeller}의 reserve 메소드 실행 결과를 반환합니다.
   */
  @PostMapping("/")
  public boolean reservation() {
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
}
