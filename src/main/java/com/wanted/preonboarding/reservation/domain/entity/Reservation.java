package com.wanted.preonboarding.reservation.domain.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "reservation_id")
    private int reservationId;
    
    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    private UUID performanceId;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;
    
    @Column(nullable = false)
    private int round;
    
    @Column(nullable = false)
    private int gate;
    
    @Column(nullable = false)
    private char line;
    
    @Column(nullable = false)
    private int seat;
    
}
