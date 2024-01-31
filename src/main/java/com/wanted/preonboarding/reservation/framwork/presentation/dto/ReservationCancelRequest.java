package com.wanted.preonboarding.reservation.framwork.presentation.dto;

import java.util.UUID;

public record ReservationCancelRequest(
	String userName,
	String phoneNumber,
	UUID reserveItemNo
)
{

}
