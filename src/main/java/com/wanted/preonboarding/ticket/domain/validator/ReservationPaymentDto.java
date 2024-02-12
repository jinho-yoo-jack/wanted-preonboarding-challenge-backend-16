package com.wanted.preonboarding.ticket.domain.validator;

import com.wanted.preonboarding.core.code.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationPaymentDto {

    private int performancePrice;
    private BigDecimal directPaymentPrice;
    private Map<DiscountType, Integer> discountTypeAndRateMap;
}
