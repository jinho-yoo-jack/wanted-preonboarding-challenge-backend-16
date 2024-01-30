package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.util.UUID;

/**
 * DB Table = performance
 * 공연 정보 엔티티
 * private UUID id;    // 공연/전시 ID  (PK)
 * private String name;    // 공연/전시 이름
 * private int price;  // 가격
 * private int round;  // 공연/전시 회차
 * private int type;   // 공연 타입 ('NONE, CONCERT, EXHIBITION')
 * private Date start_date;    // 공연 일시
 * private String isReserve;   // 예약 여부
 */
@Entity
@DynamicInsert
@Table(name = "performance")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Performance {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;    // 공연/전시 ID
    @Column(nullable = false)
    private String name;    // 공연/전시 이름
    @Column(nullable = false)
    private int price;  // 가격

    @Column(unique = true ,nullable = false)
    private int round;  // 공연/전시 회차
    @Column(nullable = false)
    private int type;   // 공연 타입 ('NONE, CONCERT, EXHIBITION')
    @Column(nullable = false)
    private LocalDateTime start_date;    // 공연 일시
    @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar default 'disable'")
    private String isReserve;   // 예약 여부
    @Column(nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private LocalDateTime createdAt;    // 생성 시각

    @Column(nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private LocalDateTime updatedAt;     // 수정 시각

}
