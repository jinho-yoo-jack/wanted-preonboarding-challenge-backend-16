package com.wanted.preonboarding.domain.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.preonboarding.domain.common.domain.response.ResponseHandler;
import com.wanted.preonboarding.domain.common.utils.ResponseUtils;
import com.wanted.preonboarding.domain.reservation.containerservice.ReservationNotificationContainerService;
import com.wanted.preonboarding.domain.reservation.domain.request.ReservationNotificationCreateRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservation-notification")
@RequiredArgsConstructor
public class ReservationNotificationController {

	private final ReservationNotificationContainerService reservationNotificationContainerService;

	@PostMapping(value = "")
	public ResponseEntity<ResponseHandler<Void>> createReservationNotification(
		@RequestBody ReservationNotificationCreateRequest request) {

		this.reservationNotificationContainerService.createReservationNotification(request);
		return ResponseUtils.resultOk();
	}



}
