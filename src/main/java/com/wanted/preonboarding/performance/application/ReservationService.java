package com.wanted.preonboarding.performance.application;

import com.wanted.preonboarding.performance.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.performance.presentation.dto.ReservationResponse;
import com.wanted.preonboarding.performance.domain.PerformanceReservation;
import com.wanted.preonboarding.performance.domain.PerformanceShowing;
import com.wanted.preonboarding.performance.infrastructure.repository.ReservationRepository;
import com.wanted.preonboarding.performance.infrastructure.repository.ShowingRepository;
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
		PerformanceShowing performanceShowing = showingRepository.findById(request.performanceId())
			.orElseThrow(EntityNotFoundException::new);

		PerformanceReservation performanceReservation = performanceShowing.reserve(request);

		if (performanceShowing.isReservationAvailable()) {
			// 1. 결제
			paymentGatewayStub(performanceReservation);

			// 2. 예매 진행
			reservationRepository.save(performanceReservation);
		}
		return ReservationResponse.of(performanceReservation);
	}

	private void paymentGatewayStub(PerformanceReservation performanceReservation) {
		log.info("결제 성공");
	}

}
