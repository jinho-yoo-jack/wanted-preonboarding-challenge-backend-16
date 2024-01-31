package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//공연좌석 저장소
@Entity
@Table(name = "performance_seat_info")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceSeatInfo {

    @Id
    private Long id;
    String section;

}
