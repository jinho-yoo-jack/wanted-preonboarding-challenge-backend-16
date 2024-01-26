package com.wanted.preonboarding.stage.dto.request;

import com.wanted.preonboarding.stage.domain.constant.SeatStatus;
import com.wanted.preonboarding.stage.dto.SeatInfo;
import com.wanted.preonboarding.stage.dto.StageInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class StageRequest {
    private String stageName;
    private String address;
    private List<SeatRequest> seats;

    public StageRequest() {
        this.seats = new ArrayList<>();
    }

    public StageInfo toDto() {
        return StageInfo.builder()
                .stageName(stageName)
                .address(address)
                .seats(seats.stream().map(SeatRequest::toDto).collect(Collectors.toList()))
                .build();
    }

    @Getter
    @Setter
    public static class SeatRequest {
        private String seatName;
        private String grade;
        private String seatStatus;
        private Integer price;

        public SeatInfo toDto() {
            return SeatInfo.builder()
                    .seatName(seatName)
                    .grade(grade)
                    .seatStatus(seatStatus != null ? SeatStatus.valueOf(seatStatus) : SeatStatus.EMPTY)
                    .price(price)
                    .build();
        }
    }

}
