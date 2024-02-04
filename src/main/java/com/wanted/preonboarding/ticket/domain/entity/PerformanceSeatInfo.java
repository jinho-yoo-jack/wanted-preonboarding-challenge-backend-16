package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceSeatInfo extends DateBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    private UUID performanceId;
    @Embedded
    private SeatInfo seatInfo;
    private String isReserve;
    @OneToOne(fetch = FetchType.LAZY)
    private Reservation reservation;

    @Builder
    public PerformanceSeatInfo(UUID performanceId, SeatInfo seatInfo, String isReserve) {
        this.performanceId = performanceId;
        this.seatInfo = seatInfo;
        this.isReserve = isReserve;
    }

    public void reserved()
    {
        this.isReserve = "disable";
    }

    public void addReservation(Reservation reservation)
    {
        this.reservation = reservation;
    }

    public void cancelReservation() {
        this.isReserve = "enable";
        this.reservation = null;
    }
}
