package com.wanted.preonboarding.performanceSeat.domain.entity;

import com.wanted.preonboarding.common.model.PerformanceId;
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

    @Embedded
    private PerformanceId performanceId;

    @Embedded
    private SeatInfo seatInfo;

    public UUID getPerformanceId() {
        return this.performanceId.getValue();
    }
}
