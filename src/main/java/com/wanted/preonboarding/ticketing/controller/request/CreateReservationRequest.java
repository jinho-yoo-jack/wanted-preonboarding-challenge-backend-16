package com.wanted.preonboarding.ticketing.controller.request;

import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import com.wanted.preonboarding.ticketing.domain.entity.Reservation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CreateReservationRequest {
    @NotBlank(message = "예약자 이름은 필수 입력 사항입니다")
    private String reservationName;

    @NotBlank(message = "전화번호는 필수 입력 사항입니다")
    private String phoneNumber;

    @NotNull(message = "잔액은 필수 입력 사항입니다")
    private Integer balance;

    @NotNull(message = "공연 ID는 필수 입력 사항입니다")
    private UUID performanceId;

    @NotNull(message = "좌석 ID는 필수 입력 사항입니다")
    private Long seatId;

    @NotNull(message = "라운드는 필수 입력 사항입니다")
    private Integer round;

    @NotNull(message = "게이트는 필수 입력 사항입니다.")
    private Integer gate;

    @NotBlank(message = "라인은 필수 입력 사항입니다")
    private String line;

    @NotNull(message = "좌석 번호는 필수 입력 사항입니다")
    private Integer seat;

    @NotBlank(message = "예약 상태는 필수 입력 사항입니다")
    private String reservationStatus;

    @NotNull(message = "군인 여부는 필수 입력 사항입니다")
    private Boolean isSolider;


    public Reservation fromTicket(Performance performance) {
        return Reservation.builder()
                .name(reservationName)
                .phoneNumber(phoneNumber)
                .performance(performance)
                .round(round)
                .gate(gate)
                .line(line)
                .seat(seat)
                .build();
    }
}
