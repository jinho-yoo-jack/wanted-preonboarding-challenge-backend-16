package com.wanted.preonboarding.performanceSeat.domain.entity;

import com.wanted.preonboarding.common.model.ReservableEntity;
import com.wanted.preonboarding.common.model.SeatInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "performance_seat_info")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceSeatInfo extends ReservableEntity {

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
