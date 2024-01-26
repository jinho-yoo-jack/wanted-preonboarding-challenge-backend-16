package com.wanted.preonboarding.stage.dto;

import com.wanted.preonboarding.stage.domain.Seat;
import com.wanted.preonboarding.stage.domain.constant.SeatStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatInfo {
    private String seatName;
    private String grade;
    private SeatStatus seatStatus;
    private Integer price;
    public static SeatInfo of(Seat seat) {
        return SeatInfo.builder()
                .seatName(seat.getName())
                .grade(seat.getGrade())
                .seatStatus(seat.getSeatStatus())
                .price(seat.getPrice())
                .build();
    }
    public Seat toEntity() {
        return Seat.builder()
                .name(seatName)
                .grade(grade)
                .seatStatus(seatStatus)
                .price(price)
                .build();
    }
}
