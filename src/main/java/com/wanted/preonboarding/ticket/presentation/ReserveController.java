package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.CancelReserveRequest;
import com.wanted.preonboarding.ticket.domain.dto.CheckReserveRequest;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
    private final TicketSeller ticketSeller;


    //TODO: 공연 자리 예약 요청
    @Transactional
    @PostMapping
    public boolean reservation(@RequestBody ReserveInfo reserveInfo) {

        reserveInfo.setReserveInfo("Requested");

        return ticketSeller.reserve(reserveInfo);


    }


    //TODO: 예약 확인 조회
    @PostMapping("/checkReservation")
    public ResponseEntity<Reservation> checkReservation(@RequestBody CheckReserveRequest checkReserveRequest) {
        Reservation reservation = ticketSeller.checkReservation(checkReserveRequest);

        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    //TODO: 예약 취소
    @PostMapping("/cancleReservation")
    public boolean CancleReservation(@RequestBody CancelReserveRequest cancelReserveRequest) {
        boolean result = ticketSeller.cancleReserve(cancelReserveRequest);

        return result;

    }



    //TODO: 알림 서비스 :
    // 특정 공연에 대해서 취소 건이 발생하는 경우, 알림 신청을 해놓은 고객에게 취소된 예약이 있다는 사실을 알리는 알림 서비스
}
