package com.wanted.preonboarding.reservation.presentation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.preonboarding.reservation.application.ReservationService;
import com.wanted.preonboarding.reservation.domain.dto.ReserveGroupOrder;
import com.wanted.preonboarding.reservation.domain.dto.ReserveInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
    private final ReservationService reservationService;
    
    @PostMapping("/check")
    public ResponseEntity<List<ReserveInfo>> selectReserveInfo(@RequestBody @Validated(ReserveGroupOrder.selectReserveInfo.class) ReserveInfo reserveInfo) {
    	
        return ResponseEntity.ok(reservationService.selectReserveInfo(reserveInfo));
    }
    
    @PostMapping("/reserve")
    public ResponseEntity<ReserveInfo> reservation(
    		@RequestBody @Validated(ReserveGroupOrder.reservation.class
    				) ReserveInfo reserveInfo) {
    	return ResponseEntity.ok(reservationService.saveReservation(reserveInfo));
    }
    
    @PostMapping("/cancel")
    public ResponseEntity<ReserveInfo> cancleReservation(
    		@RequestBody @Validated(ReserveGroupOrder.reservation.class
    				) ReserveInfo reserveInfo) {
    	return ResponseEntity.ok(reservationService.cancelReservation(reserveInfo));
    }
}
