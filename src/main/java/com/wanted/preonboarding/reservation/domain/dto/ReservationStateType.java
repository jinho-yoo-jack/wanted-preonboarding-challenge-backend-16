package com.wanted.preonboarding.reservation.domain.dto;

public enum ReservationStateType {
	RESERVATION(0),
	WAITING(1),
	CANCELLATION(2),
	NOT_ENOUGH_MONEY(3);

	 private final int state;

	 ReservationStateType(int state) {
	     this.state = state;
	 }

	 public int getCategory() {
	     return state;
	 }
}
