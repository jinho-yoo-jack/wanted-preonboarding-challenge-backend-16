package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.ticket.presentation.dto.ReservationResponse;
import com.wanted.preonboarding.ticket.domain.Reservation;
import com.wanted.preonboarding.ticket.domain.Showing;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ShowingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReservationService {

	private final ReservationRepository reservationRepository;
	private final ShowingRepository showingRepository;


	public ReservationResponse reserve(ReservationRequest request) {
		log.info("ReservationRequest ID => {}", request.performanceId());
		Showing showing = showingRepository.findById(request.performanceId())
			.orElseThrow(EntityNotFoundException::new);

		Reservation reservation = showing.reserve(request);

		if (showing.isReservationAvailable()) {
			// 1. 결제
			paymentGatewayStub(reservation);

			// 2. 예매 진행
			reservationRepository.save(reservation);
		}
		return ReservationResponse.of(reservation);
	}

	private void paymentGatewayStub(Reservation reservation) {
		log.info("결제 성공");
	}

}
