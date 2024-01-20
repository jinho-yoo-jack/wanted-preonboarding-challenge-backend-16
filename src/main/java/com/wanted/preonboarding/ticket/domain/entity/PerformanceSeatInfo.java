package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceSeatInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    private UUID performanceId;
    @Column(nullable = false)
    private int round;
    private int gate;
    private char line;
    private int seat;
    @Setter
    @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar default 'disable'")
    private String isReserve;


}
