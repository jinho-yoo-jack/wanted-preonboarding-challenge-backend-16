package com.wanted.preonboarding.ticket.application;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.preonboarding.base.event.EventCancleTicket;
import com.wanted.preonboarding.base.rsData.RsData;
import com.wanted.preonboarding.ticket.domain.dto.ReserveResult;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
	private final ReservationRepository reservationRepository;

	private final ApplicationEventPublisher publisher;

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
		// 여러 공연 예매했을 수도 있으니 여러 응답용 객체 생성
		List<ReserveResult> results = reservationList.stream()
			.map(ReserveResult::of)
			.toList();

		return RsData.of("S-1", "예매 내역 조회 성공", results);
	}

	/*
		예매 취소 기능 : 예매 내역 확인 후 본인이 맞으면 취소
		- 예매 내역이 없거나 본인이 예매한 내역이 아니면 실패

		기능 3 :예매 취소 후 Event 발생하여 해당 공연 알림 설정된 사용자 전원에게 이메일 전송
	 */

	@Transactional
	public RsData cancle(Long reservationId, String name, String phoneNumber) {
		Reservation reservation = reservationRepository.findById(reservationId).get();
		if (reservation == null) {
			return RsData.of("F-1", "이미 삭제되었거나 존재하지 않는 예매 내역입니다.");
		}
		if (!checkReservation(reservation, name, phoneNumber)) {
			return RsData.of("F-1", "예매자 본인만 취소할 수 있습니다.");
		}
		Performance performance = reservation.getPerformance();
		String beforeCancleStatus = performance.getIsReserve();

		// 예약 내역 삭제 및 자리 이용 가능하도록 변경
		reservationRepository.delete(reservation);
		PerformanceSeatInfo seatInfo = reservation.getPerformanceSeatInfo();
		seatInfo.updateIsReserve("enable");

		// 이전에 예매 불가 상태였다면 취소표로 예매 가능해졌으니 예매 가능 상태로 바꾸고 이벤트 발생
		if (beforeCancleStatus.equals("disable")) {
			performance.updateIsReserve("enable");
			// 자리 났다는 이벤트 발생 (어떤 공연에 어떤 자리)
			publisher.publishEvent(new EventCancleTicket(this, performance, seatInfo));
		}

		return RsData.of("S-1", String.format("%s님의 예매 내역 삭제 성공", name), reservation);
	}

	/*
		예매자가 맞는지 확인하는 메서드
	 */
	private boolean checkReservation(Reservation reservation, String name, String phoneNumber) {
		if (!reservation.getName().equals(name))
			return false;
		return reservation.getPhoneNumber().equals(phoneNumber);
	}
}
