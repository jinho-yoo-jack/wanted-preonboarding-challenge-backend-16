package com.wanted.preonboarding.ticket.interfaces.controller;

import java.util.List;
import java.util.UUID;

import com.wanted.preonboarding.common.util.ResponseType;
import com.wanted.preonboarding.common.util.ResponseWrapper;
import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.ReservationService;
import com.wanted.preonboarding.ticket.interfaces.dto.CustomerContactRequest;
import com.wanted.preonboarding.ticket.interfaces.dto.PerformanceResponse;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationCancelRequest;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationRequest;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
	private final ReservationService reservationService;
	private final HttpServletRequest req;

	@PostMapping("")
	public ResponseEntity<ResponseHandler<ReservationResponse>> reserve(@RequestBody ReservationRequest request) {
		return ResponseEntity
			.ok()
			.body(ResponseHandler.<ReservationResponse>builder()
				.message("예매하였습니다.")
				.statusCode(HttpStatus.OK)
				.data(reservationService.reserve(request))
				.build()
			);
	}

	@PostMapping("/cancel")
	public ResponseEntity<ResponseHandler<ResponseType>> cancel(@RequestBody ReservationCancelRequest request) {
		return ResponseEntity
			.ok()
			.body(ResponseHandler.<ResponseType>builder()
				.message("예매를 취소하였습니다.")
				.statusCode(HttpStatus.OK)
				.data(reservationService.cancel(request))
				.build()
			);
	}

	@PostMapping("/history")
	public ResponseEntity<ResponseHandler<List<ReservationResponse>>> findReserve(@RequestBody CustomerContactRequest request) {
		return ResponseEntity
			.ok()
			.body(ResponseHandler.<List<ReservationResponse>>builder()
				.message("예매 내역 조회")
				.statusCode(HttpStatus.OK)
				.data(reservationService.getReservations(request))
				.build()
			);
	}
}
