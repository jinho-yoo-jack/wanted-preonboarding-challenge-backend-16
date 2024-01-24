package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class PerformanceSeatInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "performance_seat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Performance performance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar(20) default ''")
    private ReservationStatus status;

    @Column(nullable = false)
    private int round;

    private int gate;

    private char line;

    private int seat;

    public void updateReservationStatus(final ReservationStatus status) {
        this.status = status;
    }
}
