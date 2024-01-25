package com.wanted.preonboarding.performance.domain;

import com.wanted.preonboarding.performance.domain.constant.PerformanceType;
import com.wanted.preonboarding.performance.domain.constant.ReserveStatus;
import com.wanted.preonboarding.performance.dto.PerformanceInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Performance {
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
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PerformanceType type;
    @Column(nullable = false)
    private LocalDateTime startDate;
    @Column(nullable = false)
    private LocalDateTime endDate;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, name = "is_reserve")
    private ReserveStatus reserveStatus;

    public void update(PerformanceInfo performanceInfo) {
        this.name = performanceInfo.getPerformanceName();
        this.price = performanceInfo.getPrice();
        this.round = performanceInfo.getRound();
        this.type = PerformanceType.of(performanceInfo.getPerformanceType().getCategory());
        this.startDate = performanceInfo.getStartDate();
        this.endDate = performanceInfo.getEndDate();
        this.reserveStatus = ReserveStatus.valueOf(performanceInfo.getReserveStatus().name());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Performance that = (Performance) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
