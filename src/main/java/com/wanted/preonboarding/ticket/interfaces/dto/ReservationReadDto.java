package com.wanted.preonboarding.ticket.interfaces.dto;

import com.wanted.preonboarding.ticket.domain.code.DiscountType;
import com.wanted.preonboarding.ticket.domain.entity.DiscountInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationReadDto {

    private Performance performance;
    private PerformanceSeatInfo performanceSeatInfo;
    private List<DiscountInfo> discountInfos;
    private Set<DiscountType> requestDiscountTypes;
    private String reservationHolderName;
    private String reservationHolderPhoneNumber;
    private BigDecimal balance;
}
