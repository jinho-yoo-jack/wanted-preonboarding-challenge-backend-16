package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false)
    private int round;

    private int gate;

    private char line;

    private int seat;

    public static Reservation of(final ReserveInfo info, final Performance performance) {
        return Reservation.builder()
                .performance(performance)
                .name(info.reservationName())
                .phoneNumber(info.reservationPhoneNumber())
                .round(info.round())
                .gate(1)
                .line(info.line())
                .seat(info.seat())
                .build();
    }
}
