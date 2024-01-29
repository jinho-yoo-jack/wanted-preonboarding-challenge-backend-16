package com.wanted.preonboarding.performance.domain.dto;

import com.wanted.preonboarding.performance.domain.valueObject.discount.DiscountValidatorType;
import com.wanted.preonboarding.performance.domain.valueObject.discount.DiscountCalculatorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateDiscountRequest {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private UUID performanceId;
    private Float percent;
    private Long amount;
    private Integer type;
    private DiscountValidatorType validatorType;
    private DiscountCalculatorType calculatorType;
}
