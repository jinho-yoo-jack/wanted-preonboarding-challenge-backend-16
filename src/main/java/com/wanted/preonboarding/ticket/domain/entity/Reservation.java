package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation extends DateBaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private int id;
    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    private UUID performanceId;
    @Embedded
    private ReserverInfo reserverInfo;
    @OneToOne(mappedBy = "reservation")
    private PerformanceSeatInfo performanceSeatInfo;

    @Builder
    public Reservation(
            UUID performanceId,
            ReserverInfo reserverInfo,
            PerformanceSeatInfo performanceSeatInfo
    )
    {
        this.performanceId = performanceId;
        this.reserverInfo = reserverInfo;
        this.addPerformanceSeatInfo(performanceSeatInfo);
    }

    private void addPerformanceSeatInfo(PerformanceSeatInfo performanceSeatInfo)
    {
        this.performanceSeatInfo = performanceSeatInfo;
        performanceSeatInfo.addReservation(this);
    }
}
