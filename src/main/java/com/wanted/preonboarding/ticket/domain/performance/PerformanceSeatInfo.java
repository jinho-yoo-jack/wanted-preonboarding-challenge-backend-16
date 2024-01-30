package com.wanted.preonboarding.ticket.domain.performance;

import static com.wanted.preonboarding.ticket.domain.performance.model.ReserveState.RESERVED;

import com.wanted.preonboarding.ticket.domain.BaseTimeEntity;
import com.wanted.preonboarding.ticket.domain.performance.model.ReserveState;
import com.wanted.preonboarding.ticket.domain.performance.model.ReserveStateConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
@Entity
public class PerformanceSeatInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    private UUID performanceId;
    
    @Column(nullable = false)
    private int round;

    @Column(nullable = false)
    private int gate;

    @Column(nullable = false)
    private String line;

    @Column(nullable = false)
    private int seat;

    @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar default 'disable'")
    @Convert(converter = ReserveStateConverter.class)
    private ReserveState isReserved;

    @Builder
    public PerformanceSeatInfo(UUID performanceId, int round, int gate, String line, int seat, ReserveState isReserved) {
        this.performanceId = performanceId;
        this.round = round;
        this.gate = gate;
        this.line = line;
        this.seat = seat;
        this.isReserved = isReserved;
    }

    public void reserve() {
        this.isReserved = RESERVED;
    }
}
