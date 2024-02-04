package com.wanted.preonboarding.domain.reservation.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.preonboarding.domain.common.domain.response.ResponseHandler;
import com.wanted.preonboarding.domain.common.utils.ResponseUtils;
import com.wanted.preonboarding.domain.reservation.containerservice.ReservationContainerService;
import com.wanted.preonboarding.domain.reservation.domain.request.ReservationCreateRequest;
import com.wanted.preonboarding.domain.reservation.domain.response.ReservationCreateResponse;
import com.wanted.preonboarding.domain.reservation.domain.response.ReservationPageResponse;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

	private final ReservationContainerService reservationContainerService;

	@PostMapping(value = "")
	public ResponseEntity<ResponseHandler<ReservationCreateResponse>> createReservation(
		@RequestBody ReservationCreateRequest request) {


		return ResponseUtils.resultOk(
			this.reservationContainerService.createReservation(request));
	}

	@DeleteMapping(value = "/{reservationId}")
	public ResponseEntity<ResponseHandler<Void>> deleteReservation(
		@PathVariable Long reservationId) {

		this.reservationContainerService.deleteReservation(reservationId);
		return ResponseUtils.resultOk();
	}


	@GetMapping(value = "")
	public ResponseEntity<ResponseHandler<ReservationPageResponse>> getReservationPage(
		@RequestParam(required = false) String name,
		@RequestParam(required = false) String phoneNumber,
		@RequestParam(required = false, defaultValue = "0") int page,
		@RequestParam(required = false, defaultValue = "20") int size) {

		return ResponseUtils.resultOk(
			this.reservationContainerService.getReservationPage(
				name, phoneNumber, page, size));
	}



}
