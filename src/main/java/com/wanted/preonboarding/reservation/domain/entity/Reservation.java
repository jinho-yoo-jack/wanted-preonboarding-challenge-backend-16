package com.wanted.preonboarding.reservation.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import com.wanted.preonboarding.reservation.domain.dto.ReserveInfo;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

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
