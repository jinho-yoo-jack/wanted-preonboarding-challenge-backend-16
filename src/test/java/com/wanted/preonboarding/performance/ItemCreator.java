package com.wanted.preonboarding.performance;

import com.wanted.preonboarding.reservation.domain.vo.Item;
import com.wanted.preonboarding.reservation.domain.vo.SeatInfo;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationRequest;

public class ItemCreator {

	public Item create(ReservationRequest request) {
		return Item.create(request.performId(),request.name(),request.round(), SeatInfo.create(request.line(),request.seat()));
	}
}
