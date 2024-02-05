package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.ResponsePerformanceSeatInfoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@ToString
public class PerformanceSeatInfo {
    @Id
    @GeneratedValue
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;

    private char seatLine;
    private int seatNumber;
    private boolean isReserve = true;

    public PerformanceSeatInfo(Performance performance, String seat) {
        PerformanceSeatInfo seatInfo = convertSeatInfo(seat);

        this.performance = performance;
        this.seatLine = seatInfo.getSeatLine();
        this.seatNumber = seatInfo.getSeatNumber();
    }

    public PerformanceSeatInfo(char seatLine, int seatNumber) {
        this.seatLine = seatLine;
        this.seatNumber = seatNumber;
    }

    public void setPerformance(Performance performance){
        this.performance = performance;
    }

    public static PerformanceSeatInfo of(Performance performance, String seatInfo){
        return new PerformanceSeatInfo(performance,seatInfo);
    }

    public void closedSeat(){
        this.isReserve = false;
    }

    public static PerformanceSeatInfo convertSeatInfo(String seat) {
        char line = seat.charAt(0);
        int seatNumber = Integer.parseInt(seat.substring(1));

        return new PerformanceSeatInfo(line, seatNumber);
    }
    public ResponsePerformanceSeatInfoDto convertResponseDto(){
        return new ResponsePerformanceSeatInfoDto(this.getSeatLine(),this.getSeatNumber());
    }
}
