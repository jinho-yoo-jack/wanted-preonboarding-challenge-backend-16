package com.wanted.preonboarding.domain.performance.domain.entity;

import com.wanted.preonboarding.domain.common.domain.entity.CommonEntity;
import com.wanted.preonboarding.domain.performance.domain.enums.PerformanceStatus;
import com.wanted.preonboarding.domain.performance.domain.enums.PerformanceType;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "performance")
@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Performance extends CommonEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name", columnDefinition = "varchar(255) not null comment '공연/전시 이름'")
    private String name;

    @Convert(converter = PerformanceStatus.DBConverter.class)
    @Column(name = "performance_status", columnDefinition = "varchar(255) not null comment '공연/전시 상태'")
    private PerformanceStatus performanceStatus;
    @Convert(converter = PerformanceType.DBConverter.class)
    @Column(name = "performance_type", columnDefinition = "varchar(255) not null comment '공연/전시 종류'")
    private PerformanceType performanceType;
    @Column(name = "hall_id", columnDefinition = "bigint unsigned NULL COMMENT '공연/전시장 ID'")
    private Long hallId;
    @Column(name = "price", columnDefinition = "bigint not null comment '금액'")
    private Long price;
    @Column(name = "round", columnDefinition = "bigint not null comment '회차'")
    private Integer round;
    @Column(name = "start_at", columnDefinition = "datetime COMMENT '공연 일시'")
    private ZonedDateTime startAt;

}
