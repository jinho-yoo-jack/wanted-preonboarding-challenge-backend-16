package com.wanted.preonboarding.performance.application;

import com.wanted.preonboarding.performance.domain.PerformanceReservation;
import com.wanted.preonboarding.performance.domain.PerformanceShowing;
import com.wanted.preonboarding.performance.domain.event.EventPublisher;
import com.wanted.preonboarding.performance.domain.event.ReservationCancelEvent;
import com.wanted.preonboarding.performance.domain.vo.ReservationStatus;
import com.wanted.preonboarding.performance.infrastructure.repository.ReservationRepository;
import com.wanted.preonboarding.performance.infrastructure.repository.ShowingRepository;
import com.wanted.preonboarding.performance.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.performance.presentation.dto.ReservationResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
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
	private final EventPublisher eventPublisher;

	@Transactional
	public ReservationResponse reserve(ReservationRequest request) {
		log.info("ReservationRequest ID => {}", request.showingId());
		PerformanceShowing performanceShowing = showingRepository.findById(request.showingId())
			.orElseThrow(EntityNotFoundException::new);

		PerformanceReservation performanceReservation = performanceShowing.reserve(request);
		// 1. 결제
		paymentGatewayStub(performanceReservation);

		// 2. 예매 진행
		reservationRepository.save(performanceReservation);
		return ReservationResponse.of(performanceReservation);
	}

	@Transactional
	public void cancel(UUID reservationId) {
		PerformanceReservation performanceReservation = reservationRepository.findById(
				reservationId)
			.orElseThrow(EntityNotFoundException::new);

		//예약 취소
		int refundAmount = performanceReservation.cancel();

		//환불
		paymentGatewayStub(refundAmount);

		//이벤트 발생
		UUID showingId = performanceReservation.getPerformanceShowing().getId();
		eventPublisher.cancelEventPublish(showingId, reservationId);

	}

	public ReservationResponse getReservation(UUID reservationId) {
		PerformanceReservation reservation =
			reservationRepository.findByIdAndReservationStatus(reservationId, ReservationStatus.RESERVE)
			.orElseThrow(EntityNotFoundException::new);
		return ReservationResponse.of(reservation);
	}

	private void paymentGatewayStub(int refundAmount) {
		log.info("refundAmount : {} 환불 성공",refundAmount);
	}


	private void paymentGatewayStub(PerformanceReservation performanceReservation) {
		log.info("결제 성공");
	}
}
