package com.wanted.preonboarding.ticket.presentation.controller;

import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.aop.dto.BaseResDto;
import com.wanted.preonboarding.ticket.domain.dto.request.ReadReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.CreateReservationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final TicketSeller ticketSeller;

    /**
     * 예약 시스템
     * Request Message: 고객의 이름, 휴대 전화, 결제 가능한 금액(잔고), 예약을 원하는 공연 또는 전시회ID, 회차, 좌석 정보
     * Response Message: 예매가 완료된 공연의 정보(회차, 공연명, 좌석정보, 공연ID) + 예매자 정보(이름, 연락처)
     * 특이사항: 예약 결제 시, 다양한 할인 정책이 적용될 수 있음.
     * @param createReservationRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<BaseResDto> createReservation(@RequestBody CreateReservationRequest createReservationRequest) {
        return ResponseEntity.ok(ticketSeller.createReservation(createReservationRequest));
    }


    /**
     * 예약 조회 시스템
     * Request Message: 고객의 이름, 휴대 전화
     * Response Message: 예매가 완료된 공연의 정보(회차, 공연명, 좌석정보, 공연ID) + 예매자 정보(이름, 연락처)
     * @param readReservationRequest
     * @return
     */
    @GetMapping("/customer")
    public ResponseEntity<BaseResDto> readReservation(@RequestBody ReadReservationRequest readReservationRequest) {
        return ResponseEntity.ok(ticketSeller.readReservation(readReservationRequest));
    }
}
