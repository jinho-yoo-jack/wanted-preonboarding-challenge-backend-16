package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.Builder;
import lombok.Value;

import java.util.List;

import static com.wanted.preonboarding.ticket.application.util.TimeFormatter.convertToReadableFormat;

@Value
@Builder
public class ReservationResponse {

    Integer id;
    String performanceName;
    Integer performanceRound;
    String performanceDate;
    String name;
    String phoneNumber;
    Integer round;
    Integer gate;
    Character line;
    Integer seat;
    Integer price;
    PaymentDetail paymentDetail;

    @Builder
    private record PaymentDetail(
            String name, String phoneNumber, int paidPrice, List<String> discountDetails, int remainBalance
    ) {
    }


    public static ReservationResponse of(Reservation reservation, Performance performance, PaymentResponse paymentResponse) {
        PaymentDetail paymentDetail = PaymentDetail.builder()
                .name(reservation.getName())
                .phoneNumber(reservation.getPhoneNumber())
                .paidPrice(paymentResponse.getPaidPrice())
                .discountDetails(paymentResponse.getDiscountDetails())
                .remainBalance(paymentResponse.getRemainBalance())
                .build();

        return ReservationResponse.builder()
                .id(reservation.getId())
                .performanceName(performance.getName())
                .performanceRound(performance.getRound())
                .performanceDate(convertToReadableFormat(performance.getStartDate()))
                .name(reservation.getName())
                .phoneNumber(reservation.getPhoneNumber())
                .round(reservation.getRound())
                .gate(reservation.getGate())
                .line(reservation.getLine())
                .seat(reservation.getSeat())
                .price(performance.getPrice())
                .paymentDetail(paymentDetail)
                .build();
    }

    public static ReservationResponse of(Reservation reservation, Performance performance) {
        return ReservationResponse.builder()
                .id(reservation.getId())
                .performanceName(performance.getName())
                .performanceRound(performance.getRound())
                .performanceDate(convertToReadableFormat(performance.getStartDate()))
                .name(reservation.getName())
                .phoneNumber(reservation.getPhoneNumber())
                .round(reservation.getRound())
                .gate(reservation.getGate())
                .line(reservation.getLine())
                .seat(reservation.getSeat())
                .price(performance.getPrice())
                .build();
    }
}
