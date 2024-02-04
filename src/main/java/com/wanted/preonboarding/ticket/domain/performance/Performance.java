package com.wanted.preonboarding.ticket.domain.performance;

import com.wanted.preonboarding.ticket.domain.base.BaseTimeEntity;
import com.wanted.preonboarding.ticket.domain.performance.model.PerformanceType;
import com.wanted.preonboarding.ticket.domain.performance.model.PerformanceTypeConverter;
import com.wanted.preonboarding.ticket.domain.performance.model.ReserveState;
import com.wanted.preonboarding.ticket.domain.performance.model.ReserveStateConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
@Entity
public class Performance extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int round;

    @Column(nullable = false)
    @Convert(converter = PerformanceTypeConverter.class)
    private PerformanceType type;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar default 'enable'")
    @Convert(converter = ReserveStateConverter.class)
    private ReserveState isReserve;

    @Builder
    public Performance(String name, int price, int round, PerformanceType type, LocalDateTime startDate, ReserveState isReserve) {
        this.name = name;
        this.price = price;
        this.round = round;
        this.type = type;
        this.startDate = startDate;
        this.isReserve = isReserve;
    }
}