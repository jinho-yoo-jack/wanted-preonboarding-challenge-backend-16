package com.wanted.preonboarding.ticket.application;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wanted.preonboarding.common.exception.PerformanceNotFoundException;
import com.wanted.preonboarding.common.exception.PerformanceSeatInfoNotFoundException;
import com.wanted.preonboarding.common.exception.ReservationAlreadyExistsException;
import com.wanted.preonboarding.ticket.domain.discountpolicy.DiscountPolicies;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.enumeration.ReservationStatus;
import com.wanted.preonboarding.ticket.domain.dto.ReservationDTO;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.vo.SeatInfo;
import com.wanted.preonboarding.ticket.domain.vo.UserInfo;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import com.wanted.preonboarding.ticket.interfaces.dto.CustomerContactRequest;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationCancelRequest;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationRequest;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationResponse;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final PerformanceRepository performanceRepository;
	private final PerformanceSeatInfoRepository performanceSeatInfoRepository;

	private double totalAmount = 0L;

	@Transactional
	public ReservationResponse reserve(ReservationRequest request) {
		log.info("request ID => {}", request.performanceId());
		Performance performance = performanceRepository.findPerformanceByPerformanceId(
				UUID.fromString(request.performanceId()))
			.orElseThrow(PerformanceNotFoundException::new);
		ReservationDTO reservationDTO = ReservationDTO.of(request, 0);
		totalAmount = reservationDTO.getAmount();
		validateReservation(reservationDTO);

		int price = performance.getPrice();
		DiscountPolicies discountPolicies = new DiscountPolicies();
		reservationDTO = ReservationDTO.of(request, discountPolicies.calculateRate(request.age(), price));
		reservationRepository.save(Reservation.from(reservationDTO));

		PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoRepository.findByPerformanceIdAndRoundAndSeatInfo(
				reservationDTO.getPerformanceId(),
				reservationDTO.getRound(), reservationDTO.getSeatInfo())
			.orElseThrow(PerformanceSeatInfoNotFoundException::new);
		performanceSeatInfo.reserved();
		performanceSeatInfoRepository.save(performanceSeatInfo);

		List<PerformanceSeatInfo> performanceSeatInfos = performanceSeatInfoRepository.findPerformanceSeatInfosByPerformanceIdAndReservedIsFalse(
			reservationDTO.getPerformanceId());
		if (performanceSeatInfos.isEmpty()) {
			performance.reserved();
			performanceRepository.save(performance);
		}

		return ReservationResponse.of(reservationDTO);
	}

	public List<ReservationResponse> getReservations(CustomerContactRequest request) {
		UserInfo userInfo = UserInfo.of(request.reservationName(), request.reservationPhoneNumber());
		List<Reservation> reservations = reservationRepository.findByUserInfoAndReservationStatus(userInfo,
			ReservationStatus.RESERVE);

		List<ReservationDTO> reservationDTOs = reservations
			.stream()
			.map(reservation ->
				ReservationDTO.from(reservation,
					performanceRepository.findPerformanceByPerformanceId(reservation.getPerformanceId())
						.orElseThrow(PerformanceNotFoundException::new).getPeformanceName())
			).toList();

		return reservationDTOs.stream()
			.map(ReservationResponse::of)
			.collect(Collectors.toList());
	}

	private void validateReservation(ReservationDTO reservationDTO) {
		if (reservationRepository.existsReservationByPerformanceIdAndSeatInfo(reservationDTO.getPerformanceId(),
			reservationDTO.getSeatInfo())) {
			throw new ReservationAlreadyExistsException();
		}
	}

	@Transactional
	public String cancel(ReservationCancelRequest request) {
		Reservation reservation = reservationRepository.findReservationByPerformanceIdAndSeatInfo(
			UUID.fromString(request.performanceId()), SeatInfo.of(request.line(), request.seat()));
		totalAmount -= reservation.cancel();

		PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoRepository.findByPerformanceIdAndRoundAndSeatInfo(
				reservation.getPerformanceId(),
				reservation.getRound(), reservation.getSeatInfo())
			.orElseThrow(PerformanceSeatInfoNotFoundException::new);

		performanceSeatInfo.cancel();

		reservationRepository.delete(reservation);

		return "SUCCESS";
	}
}
