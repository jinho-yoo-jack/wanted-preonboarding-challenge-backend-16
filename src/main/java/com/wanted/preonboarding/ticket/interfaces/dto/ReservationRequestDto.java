package com.wanted.preonboarding.ticket.interfaces.dto;

import com.wanted.preonboarding.core.code.DiscountType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ReservationRequestDto {

    private String reservationHolderName;
    private String reservationHolderPhoneNumber;
    private UUID performanceId;
    private int round;
    private Set<DiscountType> discounts;
    private BigDecimal balance;
    private String line;
    private int seat;
}
