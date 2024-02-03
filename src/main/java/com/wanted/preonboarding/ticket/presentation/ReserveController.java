package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.CancelReserveRequest;
import com.wanted.preonboarding.ticket.domain.dto.CheckReserveRequest;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
    private final TicketSeller ticketSeller;


    //공연 자리 예약 요청
    @PostMapping
    public ResponseEntity<ReserveInfo> reservation(@RequestBody ReserveInfo reserveInfo) {
        ReserveInfo result = ticketSeller.reserve(reserveInfo);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    //예약 확인 조회
    @PostMapping("/checkReservation")
    public ResponseEntity<List<Reservation>> checkReservation(@RequestBody CheckReserveRequest checkReserveRequest) {
        List<Reservation> reservations = ticketSeller.checkReservation(checkReserveRequest);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    // 예약 취소
    @PostMapping("/cancelReservation")
    public boolean CancelReservation(@RequestBody CancelReserveRequest cancelReserveRequest) {
        boolean result = ticketSeller.cancleReserve(cancelReserveRequest);

        return result;

    }


    //TODO: 알림 서비스 :
    // 특정 공연에 대해서 취소 건이 발생하는 경우, 알림 신청을 해놓은 고객에게 취소된 예약이 있다는 사실을 알리는 알림 서비스
}
