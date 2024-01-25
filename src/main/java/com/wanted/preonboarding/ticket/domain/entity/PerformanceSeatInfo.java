package com.wanted.preonboarding.ticket.domain.entity;

import static com.wanted.preonboarding.ticket.exception.ExceptionMessage.DISABLED_SEAT;
import static com.wanted.preonboarding.ticket.exception.ExceptionMessage.OCCUPIED_SEAT;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import com.wanted.preonboarding.ticket.exception.PerformanceSeatReserveValidationException;

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

    public void validate() {
        if (status == ReservationStatus.OCCUPIED) {
            throw new PerformanceSeatReserveValidationException(OCCUPIED_SEAT.getMessage());
        }
        if (status == ReservationStatus.DISABLED) {
            throw new PerformanceSeatReserveValidationException(DISABLED_SEAT.getMessage());
        }
    }
}
