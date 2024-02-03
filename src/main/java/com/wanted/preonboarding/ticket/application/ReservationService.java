package com.wanted.preonboarding.ticket.application;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.common.exception.PerformanceNotFoundException;
import com.wanted.preonboarding.common.exception.PerformanceSeatInfoNotFoundException;
import com.wanted.preonboarding.common.exception.ReservationAlreadyExistsException;
import com.wanted.preonboarding.ticket.domain.discountPolicy.DiscountPolicies;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.info.ReservationStatus;
import com.wanted.preonboarding.ticket.domain.info.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.info.SeatInfo;
import com.wanted.preonboarding.ticket.domain.info.UserInfo;
import com.wanted.preonboarding.ticket.domain.info.AlarmMessage;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import com.wanted.preonboarding.ticket.interfaces.dto.CustomerContactRequest;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationCancelRequest;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationRequest;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationResponse;

import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final PerformanceRepository performanceRepository;
	private final PerformanceSeatInfoRepository performanceSeatInfoRepository;
	private final PerformanceCancelSubService performanceCancelSubService;

	private double totalAmount = 0L;

	@Transactional
	public ReservationResponse reserve(ReservationRequest request) {
		log.info("request ID => {}", request.performanceId());
		Performance performance = performanceRepository.findPerformanceByPerformanceId(UUID.fromString(request.performanceId()))
			.orElseThrow(PerformanceNotFoundException::new);
		ReserveInfo reserveInfo = ReserveInfo.of(request, 0);
		totalAmount = reserveInfo.getAmount();
		validateReservation(reserveInfo);

		int price = performance.getPrice();
		DiscountPolicies discountPolicies = new DiscountPolicies();
		reserveInfo = ReserveInfo.of(request, discountPolicies.calculateRate(request.age(), price));
		reservationRepository.save(Reservation.from(reserveInfo));

		PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoRepository.findByPerformanceIdAndRoundAndSeatInfo(reserveInfo.getPerformanceId(),
			reserveInfo.getRound(), reserveInfo.getSeatInfo()).orElseThrow(PerformanceSeatInfoNotFoundException::new);
		performanceSeatInfo.reserved();
		performanceSeatInfoRepository.save(performanceSeatInfo);

		List<PerformanceSeatInfo> performanceSeatInfos = performanceSeatInfoRepository.findPerformanceSeatInfosByPerformanceIdAndReservedIsFalse(reserveInfo.getPerformanceId());
		if(performanceSeatInfos.isEmpty()) {
			performance.reserved();
			performanceRepository.save(performance);
		}

		return ReservationResponse.of(reserveInfo);
	}

	public List<ReservationResponse> getReservations(CustomerContactRequest request) {
		UserInfo userInfo = UserInfo.of(request.reservationName(), request.reservationPhoneNumber());
		List<Reservation> reservations = reservationRepository.findByUserInfoAndReservationStatus(userInfo, ReservationStatus.RESERVE);

		List<ReserveInfo> reserveInfos = reservations
			.stream()
			.map(reservation ->
				ReserveInfo.from(reservation, performanceRepository.findPerformanceByPerformanceId(reservation.getPerformanceId())
					.orElseThrow(PerformanceNotFoundException::new).getPeformanceName())
			).toList();

		return reserveInfos.stream()
			.map(ReservationResponse::of)
			.collect(Collectors.toList());
	}

	private void validateReservation(ReserveInfo reserveInfo) {
		if (reservationRepository.existsReservationByPerformanceIdAndSeatInfo(reserveInfo.getPerformanceId(),
			reserveInfo.getSeatInfo())) {
			throw new ReservationAlreadyExistsException();
		}
	}

	@Transactional
	public String cancel(ReservationCancelRequest request) {
		Reservation reservation = reservationRepository.findReservationByPerformanceIdAndSeatInfo(
				UUID.fromString(request.performanceId()), SeatInfo.of(request.line(), request.seat()));
		totalAmount -= reservation.cancel();

		PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoRepository.findByPerformanceIdAndRoundAndSeatInfo(reservation.getPerformanceId(),
			reservation.getRound(), reservation.getSeatInfo())
			.orElseThrow(PerformanceSeatInfoNotFoundException::new);

		performanceSeatInfo.cancel();

		reservationRepository.delete(reservation);

		return "SUCCESS";
	}
}
