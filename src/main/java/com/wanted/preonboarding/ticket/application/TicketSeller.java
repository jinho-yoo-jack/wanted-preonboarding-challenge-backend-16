package com.wanted.preonboarding.ticket.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.preonboarding.base.rsData.RsData;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveResult;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TicketSeller {
	private final PerformanceRepository performanceRepository;
	private final ReservationRepository reservationRepository;
	private final PerformanceSeatInfoRepository performanceSeatInfoRepository;

	/*
		기능 1. 예약 시스템
		- RequestMessage : ReserveInfor(예약자, 공연 정보)를 받아 예약하는 메서드
		- ResponseMessage : 예매가 완료된 공연 정보 + 예매자 정보
		- 특이사항 : 65세 이상 10% 할인
	 */
	@Transactional
	public RsData reserve(ReserveInfo reserveInfo) {
		log.info("reserveInfo ID => {}", reserveInfo.getPerformanceId());
		// 공연 정보 찾기
		Performance performance = performanceRepository.findById(reserveInfo.getPerformanceId())
			.orElseThrow(EntityNotFoundException::new);
		String enableReserve = performance.getIsReserve();
		// 공연이 예약 가능한 상태인지
		if (enableReserve.equals("enable")) {
			List<PerformanceSeatInfo> performanceSeats = performanceSeatInfoRepository.findAllByPerformanceId(
				performance.getId());

			PerformanceSeatInfo seat = performanceSeats.stream()
				.filter(a -> a.getRound() == reserveInfo.getRound())
				.filter(a -> a.getLine().equals(reserveInfo.getLine()))
				.filter(a -> a.getSeat() == reserveInfo.getSeat())
				.findFirst().orElse(null);

			if (seat == null) {
				return RsData.of("F-1", "해당 좌석은 존재하지 않습니다");
			}

			if (seat.getIsReserve().equals("disable")) {
				return RsData.of("F-1", "해당 좌석은 이미 다른 사용자가 예매중입니다.");
			}

			int ticketPrice = performance.getPrice();
			// 65세 이상인 경우 10% 할인, 10원 단위까지 그냥 버림
			if (reserveInfo.getAge() >= 65) {
				ticketPrice = (int)((ticketPrice * 0.9) / 10) * 10;
			}
			long userBudget = reserveInfo.getAmount();
			// 돈이 충분히 있는지 확인
			RsData ticketingRsData = canTicketing(ticketPrice, userBudget);

			// 2. 예매 진행
			if (ticketingRsData.isSuccess()) {
				reserveInfo.setAmount((Long)ticketingRsData.getData());
				// 예매 처리하고 상태 변경
				reservationRepository.save(Reservation.of(reserveInfo, performance, seat.getGate()));
				seat.updateIsReserve("disable");
				ReserveResult reserveResult = ReserveResult.builder()
					.round(reserveInfo.getRound())
					.performanceName(performance.getName())
					.performanceId(performance.getId())
					.line(seat.getLine())
					.gate(seat.getGate())
					.reservationName(reserveInfo.getReservationName())
					.reservationPhoneNumber(reserveInfo.getReservationPhoneNumber())
					.build();
				ticketingRsData = RsData.of(ticketingRsData.getResultCode(), ticketingRsData.getMsg(), reserveResult);

				performanceSeats = performanceSeatInfoRepository.findAllByPerformanceIdAndIsReserve(
					performance.getId(), "enable");

				// 자리 예매 후 빈 좌석이 없다면 상태 변경
				if(performanceSeats.isEmpty()) {
					performance.updateIsReserve("disable");
				}
			}

			return ticketingRsData;
		}
		// 예매 불가능한 공연일 경우
		else {
			return RsData.of("F-1", "해당 공연은 예약 가능한 상태가 아닙니다.");
		}
	}

	/*
		사용자 금액이 예매하기에 충분한 금액을 가지는지 검사하는 메서드
	 */
	private RsData<Long> canTicketing(int ticketPrice, long userBudget) {
		long diffBudget = userBudget - ticketPrice;
		if (diffBudget >= 0)
			return RsData.of("S-1", "예매 성공", diffBudget);
		return RsData.of("F-2", "해당 공연을 예매하기 위한 예산이 부족합니다.");
	}

}
