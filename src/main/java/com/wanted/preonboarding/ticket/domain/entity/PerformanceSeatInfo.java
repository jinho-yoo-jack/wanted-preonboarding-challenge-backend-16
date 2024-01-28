package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="performance_seat_info", uniqueConstraints = {
        @UniqueConstraint(name = "performance_seat_info_unique", columnNames = {"performance_id", "round", "line", "seat"})
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceSeatInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @Embedded
    private SeatInfo seatInfo;

    @Column(nullable = false)
    @Convert(converter = EnableDisableConverter.class)
    private Boolean isReserve;
}
