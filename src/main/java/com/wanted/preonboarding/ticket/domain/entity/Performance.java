package com.wanted.preonboarding.ticket.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.util.UUID;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Performance {

    // 공연 ID
    @JsonIgnore
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "id")
    private UUID id;

    // 공연명
    @Column(nullable = false, name = "name")
    private String name;

    // 가격
    @JsonIgnore
    @Column(nullable = false)
    private int price;

    // 회차
    @Column(nullable = false)
    private int round;

    @JsonIgnore
    @Column(nullable = false)
    private int type;

    // 시작 일시
    @Column(nullable = false, name = "start_date")
    private Date startDate;

    // 예약 가능 여부
    @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar default 'disable'")
    private String isReserve;

}
