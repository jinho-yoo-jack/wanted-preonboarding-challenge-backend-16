package com.wanted.preonboarding.ticket.controller;

import com.wanted.preonboarding.ticket.domain.dto.ReservationInfo;
import com.wanted.preonboarding.ticket.domain.dto.UserInfo;
import com.wanted.preonboarding.ticket.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    /** 공연/전시 예약 **/
    @PostMapping(value = "")
    public ResponseEntity<ReservationInfo> reservation(@RequestBody ReservationInfo reservationInfo) {
        return ResponseEntity
                .ok()
                .body(reservationService.reservation(reservationInfo));
    }

    /** 특정 유저 공연/전시 예약 조회 **/
    @GetMapping(value = "")
    public ResponseEntity<List<ReservationInfo>> getUserReservation(@RequestBody UserInfo userInfo) {
        return ResponseEntity
                .ok()
                .body(reservationService.getUserReservation(userInfo));
    }

    /** 특정 유저 공연/전시 예약 취소 **/
    @DeleteMapping(value = "")
    public ResponseEntity<ReservationInfo> cancelReservation(@RequestBody ReservationInfo reservationInfo) {
        return ResponseEntity
                .ok()
                .body(reservationService.cancelReservation(reservationInfo));
    }
}
