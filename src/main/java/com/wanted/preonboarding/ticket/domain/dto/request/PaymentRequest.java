package com.wanted.preonboarding.ticket.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.wanted.preonboarding.core.domain.util.Value.DISCOUNT_TYPE_NO;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PaymentRequest {
    private int amount;
    private String discountType = DISCOUNT_TYPE_NO;
}
