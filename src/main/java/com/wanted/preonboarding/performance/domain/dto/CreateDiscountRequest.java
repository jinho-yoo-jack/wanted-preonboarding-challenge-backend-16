package com.wanted.preonboarding.performance.domain.dto;

import com.wanted.preonboarding.performance.domain.valueObject.discount.DiscountCalculatorType;
import com.wanted.preonboarding.performance.domain.valueObject.discount.DiscountValidatorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateDiscountRequest {

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;
    private UUID performanceId;
    private Float percent;
    private Long amount;
    private Integer type;
    private DiscountCalculatorType calculatorType;
    private DiscountValidatorType validatorType;
}
