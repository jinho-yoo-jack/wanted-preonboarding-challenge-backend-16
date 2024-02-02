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
    public void reservation(@RequestBody @Validated(ReserveGroupOrder.reservation.class) ReserveInfo reserveInfo) {
    	
    	//return ResponseEntity.ok(reservationService.saveReserve(reserveInfo));
		/*
		 * return ticketSeller.reserve(ReserveInfo.builder()
		 * .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
		 * .reservationName("유진호") .reservationPhoneNumber("010-1234-1234")
		 * .reservationStatus("reserve") .amount(200000) .round(1) .line('A') .seat(1)
		 * .build() );
		 */
    }
    
    
}
