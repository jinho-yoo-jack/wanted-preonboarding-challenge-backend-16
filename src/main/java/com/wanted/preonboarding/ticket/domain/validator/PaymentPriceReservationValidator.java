package com.wanted.preonboarding.ticket.domain.validator;

import com.wanted.preonboarding.core.exception.PaymentAmountNotCorrectException;
import com.wanted.preonboarding.ticket.domain.code.DiscountType;
import com.wanted.preonboarding.ticket.domain.decorator.Payment;
import com.wanted.preonboarding.ticket.infrastructure.decorator.PerformancePayment;
import com.wanted.preonboarding.ticket.domain.entity.DiscountInfo;
import com.wanted.preonboarding.ticket.infrastructure.decorator.CJDiscountDecorator;
import com.wanted.preonboarding.ticket.infrastructure.decorator.CreditDirectDecorator;
import com.wanted.preonboarding.ticket.infrastructure.decorator.SKTDiscountDecorator;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentPriceReservationValidator implements ReservationValidator {

    @Override
    public int getOrder() {
        return 3;
    }

    @Override
    public void validate(ReservationReadDto reservationReadDto) {
        Payment payment = decoratePayment(reservationReadDto);
        ReservationPaymentDto reservationPaymentDto = getReservationPaymentDto(reservationReadDto);

        if (payment.pay(reservationPaymentDto).compareTo(BigDecimal.ZERO) != 0) {
            throw new PaymentAmountNotCorrectException();
        }
    }

    private Payment decoratePayment(ReservationReadDto reservationReadDto) {
        Payment payment = new PerformancePayment();
        for (DiscountType requestDiscountType : reservationReadDto.getRequestDiscountTypes()) {
            payment = getDiscountPaymentDecorator(requestDiscountType, payment);
        }
        payment = new CreditDirectDecorator(payment);
        return payment;
    }

    private static ReservationPaymentDto getReservationPaymentDto(ReservationReadDto reservationReadDto) {
        Map<DiscountType, Integer> discountTypeAndRateMap = reservationReadDto.getDiscountInfos().stream()
                .filter(x -> reservationReadDto.getRequestDiscountTypes().contains(x.getType()))
                .collect(Collectors.toMap(DiscountInfo::getType, DiscountInfo::getRate));

        return ReservationPaymentDto.builder()
                .performancePrice(reservationReadDto.getPerformance().getPrice())
                .directPaymentPrice(reservationReadDto.getBalance())
                .discountTypeAndRateMap(discountTypeAndRateMap)
                .build();
    }

    private Payment getDiscountPaymentDecorator(DiscountType discountType, Payment payment) {
        switch (discountType) {
            case SKT -> payment = new SKTDiscountDecorator(payment);
            case CJ -> payment = new CJDiscountDecorator(payment);
            default -> {}
        }
        return payment;
    }
}
