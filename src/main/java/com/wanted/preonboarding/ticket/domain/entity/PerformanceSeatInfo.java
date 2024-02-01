package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "performance_seat_info")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceSeatInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "performance_id", referencedColumnName = "id")
    private Performance performance;
    @Column(nullable = false)
    private int round;
    private int gate;
    private char line;
    private int seat;
    @Setter
    @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar(255) default 'disable'")
    private String isReserve;


}
