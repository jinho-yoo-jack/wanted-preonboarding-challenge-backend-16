package com.wanted.preonboarding.ticket.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "performance_seat_info")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PerformanceSeatInfo {
    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;
    
    @Column(name = "round")
    private int round;
    
    @Column(name = "gate")
    private int gate;
    
    @Column(name = "line")
    private String line;
    
    @Column(name = "seat")
    private int seat;
    
    @Column(name = "is_reserve")
    private String isReserve;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
