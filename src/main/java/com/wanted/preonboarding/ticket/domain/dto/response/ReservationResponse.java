package com.wanted.preonboarding.ticket.domain.dto.response;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.Builder;
import lombok.Value;

import java.util.List;

import static com.wanted.preonboarding.ticket.application.common.util.TimeFormatter.convertToReadableFormat;

@Value
@Builder
public class ReservationResponse {

    String code;
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

    public static ReservationResponse of(
            Reservation reservation,
            Performance performance,
            PaymentResponse paymentResponse
    ) {
        PaymentDetail paymentDetail = createPaymentDetail(reservation, paymentResponse);
        return createReservationResponse(reservation, performance, paymentDetail);
    }

    public static ReservationResponse of(Reservation reservation, Performance performance) {
        return createReservationResponse(reservation, performance, null);
    }

    private static ReservationResponse createReservationResponse(
            Reservation reservation,
            Performance performance,
            PaymentDetail paymentDetail
    ) {
        ReservationResponse.ReservationResponseBuilder builder = ReservationResponse.builder()
                .code(reservation.getCode())
                .performanceName(performance.getName())
                .performanceRound(performance.getRound())
                .performanceDate(convertToReadableFormat(performance.getStartDate()))
                .name(reservation.getName())
                .phoneNumber(reservation.getPhoneNumber())
                .round(reservation.getRound())
                .gate(reservation.getGate())
                .line(reservation.getLine())
                .seat(reservation.getSeat())
                .price(performance.getPrice());

        if (paymentDetail != null) {
            builder.paymentDetail(paymentDetail);
        }

        return builder.build();
    }

    private static PaymentDetail createPaymentDetail(Reservation reservation, PaymentResponse paymentResponse) {
        return PaymentDetail.builder()
                .name(reservation.getName())
                .phoneNumber(reservation.getPhoneNumber())
                .paidPrice(paymentResponse.getPaidPrice())
                .discountDetails(paymentResponse.getDiscountDetails())
                .remainBalance(paymentResponse.getRemainBalance())
                .build();
    }

}
