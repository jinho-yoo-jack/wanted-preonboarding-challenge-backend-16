package com.wanted.preonboarding.ticket.domain.dto;

import jakarta.persistence.Embedded;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.enumeration.ReservationStatus;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationRequest;

@Getter
@Builder
public class ReservationInfo {
	// 공연 및 전시 정보 + 예약자 정보
	private UUID performanceId;
	private String performanceName;
	@Embedded
	private UserInfo userInfo;
	private ReservationStatus reservationStatus; // 예약; 취소;
	private double amount;
	private int round;
	@Embedded
	private SeatInfo seatInfo;
	private int age;
	private int rate;

	public static ReservationInfo of(ReservationRequest request, double price) {
		return ReservationInfo.builder()
			.performanceId(UUID.fromString(request.performanceId()))
			.performanceName(request.performanceName())
			.userInfo(UserInfo.of(request.reservationName(), request.reservationPhoneNumber()))
			.round(request.round())
			.seatInfo(SeatInfo.of(request.line(), request.seat()))
			.age(request.age())
			.amount(request.amount() - price)
			.reservationStatus(ReservationStatus.RESERVE)
			.rate((int)price)
			.build();
	}

	public static ReservationInfo from(Reservation reservation, String performanceName) {
		return ReservationInfo.builder()
			.performanceId(reservation.getPerformanceId())
			.performanceName(performanceName)
			.userInfo(UserInfo.of(reservation.getUserInfo().getReservationName(),
				reservation.getUserInfo().getReservationPhoneNumber()))
			.round(reservation.getRound())
			.seatInfo(SeatInfo.of(reservation.getSeatInfo().getLine(), reservation.getSeatInfo().getSeat()))
			.reservationStatus(ReservationStatus.RESERVE)
			.build();
	}
}
