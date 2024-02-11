package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "reservation")
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
    private String name;
    
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

    public static Reservation newInstance(Performance performance, PerformanceSeatInfo seatInfo, String name, String phoneNumber) {
        return Reservation.builder()
                .performance(performance)
                .line(seatInfo.getLine())
                .seat(seatInfo.getSeat())
                .name(name)
                .phoneNumber(phoneNumber)
                .build();
    }
}
