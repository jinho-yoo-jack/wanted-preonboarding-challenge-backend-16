package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Reservation {

    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(referencedColumnName = "id", name = "performance_id"),
            @JoinColumn(referencedColumnName = "round", name = "round")
    })
    private Performance performance;
    
    @Column(name = "name")
    private String reservationHolderName;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "gate")
    private int gate;
    
    @Column(name = "line")
    private String line;
    
    @Column(name = "seat")
    private int seat;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static Reservation newInstance(Performance performance, PerformanceSeatInfo seatInfo, String reservationHolderName, String phoneNumber) {
        return Reservation.builder()
                .performance(performance)
                .line(seatInfo.getLine())
                .seat(seatInfo.getSeat())
                .reservationHolderName(reservationHolderName)
                .phoneNumber(phoneNumber)
                .build();
    }
}
