package com.wanted.preonboarding.ticket.controller;

import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    /** 공연/전시 예약 **/
    @PostMapping(value = "")
    public ResponseEntity<ReserveInfo> reservation(@RequestBody ReserveInfo reserveInfo) {
        return ResponseEntity
                .ok()
                .body(reservationService.reservation(reserveInfo));
    }

    /** 특정 유저 공연/전시 예약 조회 **/

    /** 특정 유저 공연/전시 예약 취소 **/
}
