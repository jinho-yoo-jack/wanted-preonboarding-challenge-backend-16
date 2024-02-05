package com.wanted.preonboarding.ticket.domain;

import com.wanted.preonboarding.ticket.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "performance")
public class Performance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "performance_id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "round", nullable = false)
    private int round;

    @Column(name = "start_date", updatable = false)
    private LocalDate startDate;

    @Column(name = "is_reserve", nullable = false)
    private boolean isReserve;
}
