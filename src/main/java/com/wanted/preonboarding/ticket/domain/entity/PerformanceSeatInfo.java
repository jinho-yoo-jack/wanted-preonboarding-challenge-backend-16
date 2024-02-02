package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Getter
@AllArgsConstructor
public class PerformanceSeatInfo {
    @Id
    @GeneratedValue
    private Long Id;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;
    private String seatNumber;
    private boolean isReserve = true;

    public PerformanceSeatInfo(Performance performance, String seatNumber) {
        this.performance = performance;
        this.seatNumber = seatNumber;
    }

    public static PerformanceSeatInfo of(Performance performance, String seatNumber){
        return new PerformanceSeatInfo(performance,seatNumber);
    }

    public void disable(){
        this.isReserve = false;
    }

}
