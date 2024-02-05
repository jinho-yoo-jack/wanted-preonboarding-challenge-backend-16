package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceSeatInfo;
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
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceSeat {
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
    private char line;
    @Column(nullable = false)
    private int seat;
    @Column(columnDefinition = "varchar(255) default 'disable'", nullable = false, name = "is_reserve")
    private String isReserve;

    public static PerformanceSeat of (PerformanceSeatInfo info) {
        return PerformanceSeat.builder()
                .id(info.getId())
                .performanceId(info.getPerformanceId())
                .round(info.getRound())
                .gate(info.getGate())
                .line(info.getLine())
                .seat(info.getSeat())
                .isReserve(info.getIsReserve())
                .build();
    }
}
