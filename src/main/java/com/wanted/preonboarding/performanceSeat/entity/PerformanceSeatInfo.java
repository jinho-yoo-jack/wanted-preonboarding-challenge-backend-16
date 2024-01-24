package com.wanted.preonboarding.performanceSeat.entity;

import com.wanted.preonboarding.common.model.DefaultEntity;
import com.wanted.preonboarding.common.model.SeatInfo;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "performance_seat_info")
public class PerformanceSeatInfo extends DefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    private UUID performanceId;

    @Embedded
    private SeatInfo seatInfo;

    @Column(name = "is_reserve")
    private String isReserve;

    public boolean canReserve() {
        return this.isReserve.equals("enable");
    }
}
