package com.wanted.preonboarding.ticket.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.preonboarding.base.rsData.RsData;
import com.wanted.preonboarding.ticket.domain.dto.ReserveResult;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
	private final ReservationRepository reservationRepository;

	/*
		기능 2. 예약 조회 시스템
		- RequestMessage : 고객의 이름, 휴대전화
		- ResponseMessage : 예매가 완료된 공연 정보 + 예매자 정보
	 */
	public RsData search(String name, String phoneNumber) {
		List<Reservation> reservationList = reservationRepository.findByNameAndPhoneNumber(name, phoneNumber);

		if (reservationList.isEmpty()) {
			return RsData.of("F-1", "예매 내역이 없습니다.");
		}

		List<ReserveResult> results = new ArrayList<>();
		// 여러 공연 예매했을 수도 있으니 여러 응답용 객체 생성
		for (Reservation rs : reservationList) {
			results.add(ReserveResult.builder()
				.round(rs.getRound())
				.performanceName(rs.getPerformance().getName())
				.performanceId(rs.getPerformance().getId())
				.gate(rs.getGate())
				.line(rs.getLine())
				.seat(rs.getSeat())
				.gate(rs.getGate())
				.reservationPhoneNumber(phoneNumber)
				.reservationName(name)
				.build());
		}
		return RsData.of("S-1", "예매 내역 조회 성공", results);
	}
}
