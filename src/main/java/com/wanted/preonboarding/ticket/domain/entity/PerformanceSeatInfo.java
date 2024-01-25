package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Table(name = "performance_seat_info",
        uniqueConstraints = { @UniqueConstraint(name = "performance_seat_info_unique",
                columnNames = {"performance_id", "round", "line", "seat"})})
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceSeatInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar default 'enable'")
    private String isReserve;
}
