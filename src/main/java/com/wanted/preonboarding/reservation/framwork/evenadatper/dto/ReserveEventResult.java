package com.wanted.preonboarding.reservation.framwork.evenadatper.dto;

public record ReserveEventResult(
	int paymentAmount,
	boolean isAvailable
)
{ public static ReserveEventResult create(int paymentAmount, boolean isAvailable){
        return new ReserveEventResult(paymentAmount,isAvailable);
    }}
