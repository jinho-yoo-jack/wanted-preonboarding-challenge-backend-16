package com.wanted.preonboarding.ticket.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Performance extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(nullable = false)
    private PerformanceType type;

    @Column(nullable = false)
    private int round;

    @Column(nullable = false)
    private LocalDateTime start_date;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar(20) default 'DISABLED'")
    private ReservationStatus status;

    @OneToMany(mappedBy = "performance")
    private List<PerformanceSeatInfo> seatInfos = new ArrayList<>();

    public void cancel() {
        this.status = ReservationStatus.DISABLED;
    }
}
