package com.wanted.preonboarding.reservation.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
public class ReserveItem {

	@Column(nullable = false)
	private UUID no;
	@Embedded
	private Item item;

	@Column(nullable = false)
	private LocalDate PurchaseDate;

	@Enumerated(EnumType.STRING)
	private ReservationStatus reservationStatus; // 예약; 취소

	@Column(nullable = false)
	private int fee;

	public static ReserveItem create(Item item, int fee) {
		return new ReserveItem(UUID.randomUUID(),item, LocalDate.now(), ReservationStatus.RESERVE, fee);
	}

	public int refundPolicyStub() {
		return fee;
	}

	public boolean matchItem(UUID reserveItemNo) {
		return this.no.equals(reserveItemNo);
	}

	public void cancel() {
		reservationStatus = ReservationStatus.CANCEL;
	}
}
