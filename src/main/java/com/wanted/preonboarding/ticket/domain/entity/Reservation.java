package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import com.wanted.preonboarding.ticket.domain.info.ReservationStatus;
import com.wanted.preonboarding.ticket.domain.info.ReservationInfo;
import com.wanted.preonboarding.ticket.domain.info.SeatInfo;
import com.wanted.preonboarding.ticket.domain.info.UserInfo;

@Entity
@Table
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    @Column(columnDefinition = "VARBINARY(16)", nullable = false, name = "performance_id")
    private UUID performanceId;
    @Embedded
	private UserInfo userInfo;
    @Column(nullable = false)
    private int round;
    private int gate;
	@Embedded
	private SeatInfo seatInfo;
	@Enumerated(EnumType.STRING)
	private ReservationStatus reservationStatus;
	@Column(nullable = false)
	private int rate;

    public static Reservation from(ReservationInfo info) {
        return Reservation.builder()
            .performanceId(info.getPerformanceId())
			.userInfo(UserInfo.of(info.getUserInfo().getReservationName(), info.getUserInfo().getReservationPhoneNumber()))
            .round(info.getRound())
            .gate(1)
			.seatInfo(SeatInfo.of(info.getSeatInfo().getLine(), info.getSeatInfo().getSeat()))
			.rate(info.getRate())
			.reservationStatus(info.getReservationStatus())
            .build();
    }

	public int refund() {
		return rate;
	}

	public int cancel() {
		this.reservationStatus = ReservationStatus.CANCEL;
		return refund();
	}

	public boolean matchItem(int reservationId) {
		return this.id == reservationId;
	}
}
