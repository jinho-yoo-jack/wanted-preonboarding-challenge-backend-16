package com.wanted.preonboarding.ticket.domain.entity;


import com.wanted.preonboarding.ticket.domain.dto.SubscriberPerformanceRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class ReservationAlarm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;

    private String receiver;

    @Builder
    public ReservationAlarm(Performance performance, String receiver) {
        this.performance = performance;
        this.receiver = receiver;
    }

    public static ReservationAlarm of(SubscriberPerformanceRequestDto requestDto,Performance performance){
        return ReservationAlarm.builder()
            .performance(performance)
            .receiver(requestDto.getReceiver())
            .build();

    }



}
